import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift} from "../../interfaces/ShiftEntity";
import {AssociationService} from "../../services/http/association.service";
import {CollaboratorService} from "../../services/http/collaborator.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Location} from "../../interfaces/LocationEntity";
import {LocationService} from "../../services/http/location.service";
import {ShiftAvailabilityService} from "../../services/http/shift-availability.service";
import {ShiftAvailability} from "../../interfaces/ShiftAvailabilityView";
import {CookiesService} from "../../services/token/cookies.service";
import {timer} from 'rxjs';
import {Association} from "../../interfaces/AssociationEntity";
import {TokenService} from "../../services/http/token/token.service";

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedLocation: Location | undefined;
  protected signedIn: boolean = false;
  protected dataError: boolean = false;
  protected shifts: Shift[] | undefined;
  protected shiftAvailability: ShiftAvailability[] = [];
  formData: Collaborator = {
    email: '',
    phone: '',
    firstName: '',
    lastName: '',
    age: '',
    size: 'Taglia Maglietta'
  }
  protected activeCollaborator: Collaborator | undefined;

  constructor(private shiftService: ShiftService, private route: ActivatedRoute, private associationService: AssociationService,
              private collaboratorService: CollaboratorService, private locationService: LocationService,
              protected shiftAvailabilityService: ShiftAvailabilityService, private cookiesService: CookiesService,
              private tokenService: TokenService) {
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.locationService.getLocationById(params['location']).pipe().subscribe((location: any) => {
        this.selectedLocation = location
      });

      this.shiftService.getShiftsByLocationId(params['location']).pipe().subscribe((shifts: any) => {
        this.shifts = shifts;

        shifts.sort((a: Shift, b: Shift) => {
          if (this.parseTime(a.startTime) < this.parseTime(b.startTime)) {
            return -1;
          } else if (this.parseTime(a.startTime) > this.parseTime(b.startTime)) {
            return 1;
          } else {
            return 0;
          }
        })

        for (let shift of shifts) {
          this.shiftAvailabilityService.getShiftAvailability(shift.id).pipe().subscribe((shiftAvailability: any) => {
            this.shiftAvailability.push(shiftAvailability);
          });
        }
      });
    });

    this.tokenService.getCollaboratorFromToken(this.cookiesService.getToken()).pipe().subscribe((collaborator: any) => {
      this.activeCollaborator = collaborator;
      this.resetFormData();
    });
  }

  showDialog(shiftId: number | undefined) {
    const dialog = document.getElementById(shiftId + 'Dialog') as HTMLDialogElement;
    if (dialog) {
      dialog.showModal();
    }
  }

  submitData(shiftId: number | undefined) {
    if (shiftId !== undefined && this.activeCollaborator?.id) {
      if (this.checkData()) return;

      let collaborator: Collaborator = this.formData;
      collaborator.id = this.activeCollaborator.id;
      this.collaboratorService.updateCollaborator(collaborator).pipe().subscribe();
      let association: Association = {
        id: {
          collaboratorId: this.activeCollaborator.id,
          shiftId: shiftId
        },
        status: 0
      }

      this.associationService.saveAssociation(association).pipe().subscribe();
      this.resetFormData();

      window.location.reload();
    }
  }

  checkData(): boolean {
    let error = this.formData.firstName === '' || this.formData.lastName === '' || this.formData.phone === '' || this.formData.age === '' || this.formData.size === 'Taglia Maglietta';
    this.dataError = error;

    if (error) {
      timer(5000).subscribe(() => {
        this.dataError = false;
      });
    }

    return error;
  }

  resetFormData() {
    this.formData.firstName = this.activeCollaborator?.firstName
    this.formData.lastName = this.activeCollaborator?.lastName

    if (this.activeCollaborator?.email !== undefined) {
      this.formData.email = this.activeCollaborator.email
    }
    this.formData.phone = this.activeCollaborator?.phone
    this.formData.age = this.activeCollaborator?.age
    this.formData.size = this.activeCollaborator?.size
  }

  parseTime(timeString: string): number {
    let value = timeString.replaceAll(":", "");
    return parseInt(value);
  };

}
