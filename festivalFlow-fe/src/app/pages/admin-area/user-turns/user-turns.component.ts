// user-turns.component.ts
import { Component, OnInit } from '@angular/core';
import { Collaborator } from '../../../interfaces/CollaboratorEntity';
import { CollaboratorService } from '../../../services/http/user/collaborator.service';
import { Router } from '@angular/router';
import {LocationService} from "../../../services/http/user/location.service";
import { Location } from '../../../interfaces/LocationEntity';
import {Shift} from "../../../interfaces/ShiftEntity";
import {Association} from "../../../interfaces/AssociationEntity";
import {AssociationService} from "../../../services/http/user/association.service";

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
  private readonly VALID_USER = 'demo';
  private readonly VALID_PASS = '1234';

  constructor(
    private collaboratorService: CollaboratorService,
    private locationService: LocationService,
    private associationService: AssociationService,
    private router: Router
  ) {}

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
  }

  /** Sblocca la pagina se le credenziali corrispondono */
  tryUnlock(): void {
    if (this.username === this.VALID_USER && this.password === this.VALID_PASS) {
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
        (c.lastName ?? '').toLowerCase().includes(t)  ||
        (c.email ?? '').toLowerCase().includes(t)     ||
        (c.phone ?? '').toLowerCase().includes(t)
      )
    );
  }

  getCollaboratorsByManager() {
    if (this.locations && this.shifts && this.associations) {


      const target = this.username.trim().toLowerCase();

      /* 1. Location gestite dal manager */
      const locationIds: number[] = [];
      for (const loc of this.locations) {
        if ((loc.manager?.trim().toLowerCase() ?? '') === target && loc.id !== undefined) {
          locationIds.push(loc.id);
        }
      }

      /* 2. Raccolta dei collaboratori */
      const unique = new Map<number, Collaborator>();   // id → Collaborator

      // Variante A: Hai già collaboratorEntityList sui turni
      for (const shift of this.shifts) {
        if (shift.location?.id && locationIds.includes(shift.location.id)) {
          for (const coll of shift.collaboratorEntityList ?? []) {
            if (coll.id !== undefined && !unique.has(coll.id)) {
              unique.set(coll.id, coll);
            }
          }
        }
      }

      // Variante B: usi invece la tabella di associazione
      for (const assoc of this.associations) {
        if (locationIds.includes(
          this.shifts.find(s => s.id === assoc.id.shiftId)?.location?.id ?? -1
        )) {
          const coll = assoc as unknown as { collaborator?: Collaborator }; // cast rapido
          if (coll.collaborator?.id !== undefined && !unique.has(coll.collaborator.id)) {
            unique.set(coll.collaborator.id, coll.collaborator);
          }
        }
      }

      /* 3. Risultato */
      this.collaborators = Array.from(unique.values());
    }
  }
}
