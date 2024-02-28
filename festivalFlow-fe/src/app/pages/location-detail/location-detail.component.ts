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

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedDay: Day | undefined;
  protected selectedLocation: Location | undefined;
  protected signedIn: boolean = false;
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

  constructor(private shiftService: ShiftService, private route: ActivatedRoute, private associationService: AssociationService,
              private collaboratorService: CollaboratorService, private dayService: DayService, private locationService: LocationService,
              protected shiftAvailabilityService: ShiftAvailabilityService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.locationService.getLocationById(params['location']).pipe().subscribe((location: any) => {
        this.selectedLocation = location
      });

      this.dayService.getDayById(params['day']).pipe().subscribe((day: any) => {
        this.selectedDay = day;
      });

      this.shiftService.getShiftByLocationIdAndDay(params['location'], params['day']).pipe().subscribe((shifts: any) => {
        this.shifts = shifts;

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
    console.log(this.formData);
    let collaborator: Collaborator = this.formData;
    this.collaboratorService.saveCollaborator(collaborator);

    this.resetFormData();
  }

  resetFormData() {
    this.formData = {
      email: '',
      phone: '',
      firstName: '',
      lastName: '',
      age: '',
      size: 'Taglia Maglietta'
    }
  }
}
