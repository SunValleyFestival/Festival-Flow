import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/user/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift, ShiftClient} from "../../interfaces/ShiftEntity";
import {AssociationService} from "../../services/http/user/association.service";
import {CollaboratorService} from "../../services/http/user/collaborator.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Location} from "../../interfaces/LocationEntity";
import {LocationService} from "../../services/http/user/location.service";
import {ShiftAvailabilityService} from "../../services/http/user/shift-availability.service";
import {Association} from "../../interfaces/AssociationEntity";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedLocation: Location | undefined;
  protected shifts: ShiftClient[] | undefined;
  protected activeCollaborator: Collaborator | undefined;
  protected noticeDialog: boolean = false;
  protected noticeMessage: string = '';
  protected noticeClass = '';

  protected formData: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    countryCode: ['+41'],
    phone: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    age: ['', Validators.required],
    yearsExperience: ['', Validators.required],
    town: ['', Validators.required],
    size: ['Taglia Maglietta', Validators.required],
    comment: ['']
  });

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

          this.associationService.getCollaboratorsNames(shift.id).pipe().subscribe((collaborators: any) => {
            for (let collaborator of collaborators) {
              if (!shift.collaboratorName) {
                shift.collaboratorName = '';
              }
              shift.collaboratorName += collaborator + ', ';
            }
            if (shift.collaboratorName) {
              shift.collaboratorName = shift.collaboratorName.substring(0, shift.collaboratorName.length - 2);
            }
          })
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
        this.associationService.saveAssociation(association).subscribe((response: any) => {

          if (response.status === 200) {
            this.ngOnInit();
            this.showNotice("Iscrizione effettuata con successo", 0);
          } else if (response.status === 208) {
            this.showNotice("Ti sei già iscritto a questo turno", 1);
          } else {
            this.showNotice("Errore durante la registrazione", 2);
          }
        }, (error: any) => {
          if (error.status === 403) {
            this.showNotice("Non puoi iscriverti a questo turno", 1);
          } else {
            this.showNotice("Errore durante la registrazione", 2);
          }
        });
      });


    }
  }

  initFormData() {
    this.formData.get('email')?.setValue(this.activeCollaborator?.email);
    if (this.activeCollaborator?.phone) {
      this.formData.get('countryCode')?.setValue(this.activeCollaborator?.phone.substring(0, 3));
      this.formData.get('phone')?.setValue(this.activeCollaborator?.phone.substring(3));
    } else {
      this.formData.get('countryCode')?.setValue('+41');
      this.formData.get('phone')?.setValue('');
    }
    this.formData.get('firstName')?.setValue(this.activeCollaborator?.firstName);
    this.formData.get('lastName')?.setValue(this.activeCollaborator?.lastName);
    this.formData.get('age')?.setValue(this.activeCollaborator?.age);
    this.formData.get('yearsExperience')?.setValue(this.activeCollaborator?.yearsExperience);
    this.formData.get('town')?.setValue(this.activeCollaborator?.town);
    this.formData.get('size')?.setValue(this.activeCollaborator?.size);
  }

  getCollaboratorFromFormData(): Collaborator {
    return {
      email: this.formData.get('email')?.value,
      phone: this.formData.get('countryCode')?.value + this.formData.get('phone')?.value,
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

  showNotice(notice: string, noticeType: number) {

    if (noticeType == 0) {
      this.noticeClass = 'alert-success';
    } else if (noticeType == 1) {
      this.noticeClass = 'alert-warning';
    } else {
      this.noticeClass = 'alert-error';
    }

    this.noticeMessage = notice;
    this.noticeDialog = true;
    setTimeout(() => {
      this.noticeDialog = false;
    }, 3000);
  }
}
