import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift, ShiftClient} from "../../interfaces/ShiftEntity";
import {AssociationService} from "../../services/http/association.service";
import {CollaboratorService} from "../../services/http/collaborator.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Location} from "../../interfaces/LocationEntity";
import {LocationService} from "../../services/http/location.service";
import {ShiftAvailabilityService} from "../../services/http/shift-availability.service";
import {timer} from 'rxjs';
import {Association} from "../../interfaces/AssociationEntity";
import {NavigationService} from "../../services/navigation/navigation.service";

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedLocation: Location | undefined;
  protected signedIn: boolean = false;
  protected dataError: boolean = false;
  protected shifts: ShiftClient[] | undefined;
  formData: Collaborator = {
    email: '',
    phone: '',
    firstName: '',
    lastName: '',
    age: '',
    size: 'Taglia Maglietta'
  }
  protected activeCollaborator: Collaborator | undefined;

  constructor(
    private shiftService: ShiftService,
    private route: ActivatedRoute,
    private associationService: AssociationService,
    private collaboratorService: CollaboratorService,
    private locationService: LocationService,
    protected shiftAvailabilityService: ShiftAvailabilityService,
    private navigationService: NavigationService
  ) {
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
            shift.shiftAvailability = shiftAvailability.availableSlots
          });
        }
      });
    });


    this.collaboratorService.getCollaboratorFromToken().pipe().subscribe((collaborator: any) => {
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
      let association: Association = {
        id: {
          collaboratorId: this.activeCollaborator.id,
          shiftId: shiftId
        },
        status: 0
      }

      this.collaboratorService.updateCollaborator(collaborator).pipe().subscribe(() => {
        this.associationService.saveAssociation(association).pipe().subscribe();
        this.resetFormData();
      });


    }
  }

  checkData(): boolean {
    let error = this.formData.firstName === '' || this.formData.lastName === '' ||
      this.formData.phone === '' || this.formData.age === '' || this.formData.size === 'Taglia Maglietta' ||
      this.formData.yearsExperience === undefined;

    this.dataError = error;

    if (error) {
      timer(5000).subscribe(() => {
        this.dataError = false;
      });
    }

    return error;
  }

  resetFormData() {
    if (this.activeCollaborator) {
      this.formData = {
        email: this.activeCollaborator.email,
        phone: this.activeCollaborator.phone || '',
        firstName: this.activeCollaborator.firstName || '',
        lastName: this.activeCollaborator.lastName || '',
        age: this.activeCollaborator.age || '',
        size: this.activeCollaborator.size || 'Taglia Maglietta',
        yearsExperience: this.activeCollaborator.yearsExperience || undefined,
      }
    }
  }

  parseTime(timeString: string): number {
    let value = timeString.replaceAll(":", "");
    return parseInt(value);
  };
}
