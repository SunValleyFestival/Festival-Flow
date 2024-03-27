import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CollaboratorService} from "../../services/http/collaborator.service";
import {AssociationService} from "../../services/http/association.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Association} from "../../interfaces/AssociationEntity";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  protected activeUser: Collaborator = {} as Collaborator;
  protected associations: Association[] = [];
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
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.collaboratorService.getCollaboratorFromToken().subscribe((collaborator: Collaborator) => {
      this.activeUser = collaborator;
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.associationService.getAssociationByCollaboratorId(params['id']).subscribe((associations: any[]) => {
          this.associations = associations;
        });
      }
    });

    this.collaboratorService.getCollaboratorFromToken().pipe().subscribe((collaborator: any) => {
      this.activeCollaborator = collaborator;
      this.initFormData();
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
}
