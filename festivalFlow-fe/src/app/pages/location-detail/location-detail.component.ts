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
import {Association} from "../../interfaces/AssociationEntity";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedLocation: Location | undefined;
  protected dataError: boolean = false;
  protected shifts: ShiftClient[] | undefined;
  protected activeCollaborator: Collaborator | undefined;

  protected formData!: FormGroup;

  constructor(
    private shiftService: ShiftService,
    private route: ActivatedRoute,
    private associationService: AssociationService,
    private collaboratorService: CollaboratorService,
    private locationService: LocationService,
    private shiftAvailabilityService: ShiftAvailabilityService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {

    this.formData = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      phone: [0, [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      age: ['', Validators.required],
      yearsExperience: ['', Validators.required],
      town: ['', Validators.required],
      size: ['', Validators.required],
      comment: ['']
    })

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
      this.initFormData();
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

      let collaborator: Collaborator = this.getCollaboratorFromFormData();
      collaborator.id = this.activeCollaborator.id;
      let association: Association = {
        id: {
          collaboratorId: this.activeCollaborator.id,
          shiftId: shiftId
        },
        status: 0,
        comment: this.formData.get('comment')?.value
      }

      this.collaboratorService.updateCollaborator(collaborator).pipe().subscribe(() => {
        this.associationService.saveAssociation(association).pipe().subscribe();
      });


    }
  }

  initFormData() {
    this.formData = this.fb.group({
      email: [this.activeCollaborator?.email, [Validators.required, Validators.email]],
      phone: [this.activeCollaborator?.phone, [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
      firstName: [this.activeCollaborator?.firstName, [Validators.required]],
      lastName: [this.activeCollaborator?.lastName, [Validators.required]],
      age: [this.activeCollaborator?.age, [Validators.required]],
      yearsExperience: [this.activeCollaborator?.yearsExperience, [Validators.required]],
      town: [this.activeCollaborator?.town, [Validators.required]],
      size: [this.activeCollaborator?.size, [Validators.required]],
      comment: ''
    });
  }

  getCollaboratorFromFormData(): Collaborator {
    return {
      email: this.formData.get('email')?.value,
      phone: this.formData.get('phone')?.value,
      firstName: this.formData.get('firstName')?.value,
      lastName: this.formData.get('lastName')?.value,
      age: this.formData.get('age')?.value,
      yearsExperience: this.formData.get('yearsExperience')?.value,
      town: this.formData.get('town')?.value,
      size: this.formData.get('size')?.value,
    };
  }

  parseTime(timeString: string): number {
    let value = timeString.replaceAll(":", "");
    return parseInt(value);
  };
}
