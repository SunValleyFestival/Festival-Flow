import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CollaboratorService} from "../../services/http/user/collaborator.service";
import {AssociationService} from "../../services/http/user/association.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Association, AssociationClient} from "../../interfaces/AssociationEntity";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ShiftService} from "../../services/http/shift.service";
import {Shift} from "../../interfaces/ShiftEntity";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  protected associations: AssociationClient[] = [];
  protected activeCollaborator: Collaborator | undefined;

  protected formData: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    countryCode: ['+41'],
    phone: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    age: ['', Validators.required],
    yearsExperience: ['', Validators.required],
    town: ['', Validators.required],
    size: ['', Validators.required],
  });


  constructor(
    private route: ActivatedRoute,
    private collaboratorService: CollaboratorService,
    private associationService: AssociationService,
    private shiftService: ShiftService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.collaboratorService.getCollaboratorFromToken().pipe().subscribe((collaborator: any) => {
      this.activeCollaborator = collaborator;
      this.initFormData();

      this.route.params.subscribe(params => {

        if (this.activeCollaborator != undefined && this.activeCollaborator.id) {

          this.associationService.getAssociationByCollaboratorId(this.activeCollaborator.id).subscribe((associations: Association[]) => {

            for (let association of associations) {
              this.shiftService.getShiftById(association.id.shiftId).subscribe((shift: Shift) => {
                let associationClient: AssociationClient = {
                  collaboratorId: association.id.collaboratorId,
                  status: association.status,
                  shift: shift
                };
                this.associations.push(associationClient);
              })
            }
          });
        }
      });
    });
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

  saveUser() {
    if (this.activeCollaborator?.id) {
      let collaborator = this.getCollaboratorFromFormData();
      collaborator.id = this.activeCollaborator.id
      this.collaboratorService.updateCollaborator(collaborator).pipe().subscribe(() => {
        this.ngOnInit();
      });
    }
  }

  getCollaboratorFromFormData()
    :
    Collaborator {
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
}
