// user-turns.component.ts
import {Component, OnInit} from '@angular/core';
import {Collaborator} from '../../interfaces/CollaboratorEntity';
import {CollaboratorService} from '../../services/http/user/collaborator.service';
import {Router} from '@angular/router';
import {LocationService} from "../../services/http/user/location.service";
import {Location} from '../../interfaces/LocationEntity';
import {Shift} from "../../interfaces/ShiftEntity";
import {Association} from "../../interfaces/AssociationEntity";
import {AssociationService} from "../../services/http/user/association.service";
import {ShiftService} from "../../services/http/user/shift.service";

@Component({
  selector: 'app-user-turns',
  templateUrl: './user-turns.component.html',
  styleUrls: ['./user-turns.component.css']
})
export class UserTurnsComponent implements OnInit {
  collaborators: Collaborator[] = [];
  filteredCollaborators: Collaborator[] = [];
  nameToFilter = '';

  locations: Location[] | undefined;          // tutte le location
  shifts: Shift[] | undefined;                // tutti i turni
  associations: Association[] | undefined;    // link Collaborator-Shift (opzionale)


  // --- PROPRIETÀ PER IL "LOGIN" FRONTEND ---
  unlocked = false;               // se true mostri la tabella
  username = '';                  // bind al campo input utente
  password = '';                  // bind al campo input password
  error = '';                     // messaggio di errore

  // credenziali “hard-coded” (cambiale a tuo piacere)
  private readonly VALID_PASS = 'SVF_TURNI';

  constructor(
    private collaboratorService: CollaboratorService,
    private locationService: LocationService,
    private associationService: AssociationService,
    private shiftService: ShiftService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.collaboratorService.getCollaborators().subscribe((collaborators: Collaborator[]) => {
      this.collaborators = collaborators;
    });

    this.locationService.getLocations().subscribe((locations: Location[]) => {
      this.locations = locations;
    });

    this.associationService.getAssociations().subscribe((associations: Association[]) => {
      this.associations = associations;
    });

    this.shiftService.getAll().subscribe((shifts: Shift[]) => {
      this.shifts = shifts;
    });

  }

  /** Sblocca la pagina se le credenziali corrispondono */
  tryUnlock(): void {
    if (this.password === this.VALID_PASS) {
      this.unlocked = true;
      this.error = '';

      this.getCollaboratorsByManager()

    } else {
      this.error = 'Credenziali non valide';
    }
  }

  goToDetailPage(id?: number): void {
    if (id) {
      this.router.navigate(['admin/user/' + id]);
    }
  }

  filterFirstName(): void {
    const terms = this.nameToFilter.toLowerCase().trim().split(' ');
    this.filteredCollaborators = this.collaborators.filter(c =>
      terms.some(t =>
        (c.firstName ?? '').toLowerCase().includes(t) ||
        (c.lastName ?? '').toLowerCase().includes(t) ||
        (c.email ?? '').toLowerCase().includes(t) ||
        (c.phone ?? '').toLowerCase().includes(t)
      )
    );
  }

  getCollaboratorsByManager() {
    console.log(this.locations?.length, this.shifts?.length, this.associations?.length);

    if (this.locations && this.shifts && this.associations) {
      const target = this.username.trim().toLowerCase();

      /* 1. Location gestite dal manager */
      const locationIds: number[] = [];
      for (const loc of this.locations) {
        const managerName = loc.manager?.trim().toLowerCase() ?? '';
        if (managerName === target && loc.id !== undefined) {
          locationIds.push(loc.id);
        }
      }

      /* 2. Raccolta dei collaboratori */
      const unique = new Map<number, Collaborator>();   // id → Collaborator

      // Variante B: usi invece la tabella di associazione
      for (const assoc of this.associations) {
        const shift = this.shifts.find(s => s.id === assoc.id.shiftId);
        const assocLocId = shift?.location?.id ?? -1;

        if (locationIds.includes(assocLocId)) {
          const coll = this.collaborators.find(c => c.id === assoc.id.collaboratorId) as Collaborator;

          if (coll.id !== undefined && !unique.has(coll.id)) {
            unique.set(coll.id, coll);
          }
        }
      }

      /* 3. Risultato */
      this.collaborators = Array.from(unique.values());

      this.filteredCollaborators = this.collaborators;

      console.log(
        `[getCollaboratorsByManager] Collaboratori finali (${this.collaborators.length}):`,
        this.collaborators
      );
    } else {
      console.warn('[getCollaboratorsByManager] Dati non disponibili: locations, shifts o associations mancanti');
    }
  }
}
