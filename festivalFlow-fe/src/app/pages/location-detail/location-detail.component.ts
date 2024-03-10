import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift} from "../../interfaces/ShiftEntity";
import {AssociationService} from "../../services/http/association.service";
import {CollaboratorService} from "../../services/http/collaborator.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Location} from "../../interfaces/LocationEntity";
import {Day} from "../../interfaces/DayEntity";
import {DayService} from "../../services/http/day.service";
import {LocationService} from "../../services/http/location.service";
import {ShiftAvailabilityService} from "../../services/http/shift-availability.service";
import {ShiftAvailability} from "../../interfaces/ShiftAvailabilityView";
import {CookiesService} from "../../services/token/cookies.service";

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedLocation: Location | undefined;
  protected signedIn: boolean = false;
  protected shifts: Shift[] | undefined;
  protected shiftAvailability: ShiftAvailability[] = [];

  formData: Collaborator = {
    email: this.cookiesService.getUserEmail(),
    phone: '',
    firstName: '',
    lastName: '',
    age: '',
    size: 'Taglia Maglietta'
  }

  constructor(private shiftService: ShiftService, private route: ActivatedRoute, private associationService: AssociationService,
              private collaboratorService: CollaboratorService, private dayService: DayService, private locationService: LocationService,
              protected shiftAvailabilityService: ShiftAvailabilityService, private cookiesService: CookiesService) {
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
  }

  showDialog(shiftId: number | undefined) {
    const dialog = document.getElementById(shiftId + 'Dialog') as HTMLDialogElement;
    if (dialog) {
      dialog.showModal();
    }
  }

  submitData(shiftId: number | undefined) {

    if (shiftId !== undefined) {
      let collaborator: Collaborator = this.formData;
      collaborator.id = this.cookiesService.getUserId();
      this.collaboratorService.saveCollaborator(collaborator);

      this.associationService.saveAssociation(this.cookiesService.getUserId(), shiftId);
      this.resetFormData();
    }
  }

  resetFormData() {
    this.formData = {
      email: this.cookiesService.getUserEmail(),
      phone: '',
      firstName: '',
      lastName: '',
      age: '',
      size: 'Taglia Maglietta'
    }
  }

  parseTime(timeString: string): number {
    let value = timeString.replaceAll(":", "");
    return parseInt(value);
  };

}
