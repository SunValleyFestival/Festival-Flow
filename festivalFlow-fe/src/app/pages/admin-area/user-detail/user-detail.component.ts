import {Component, OnInit} from '@angular/core';
import {CollaboratorService} from "../../../services/http/collaborator.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  protected dataError: boolean = false
  protected collaborator: Collaborator = {} as Collaborator;

  protected collaboratorForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    countryCode: ['+41'],
    phone: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    age: ['', Validators.required],
    size: ['', Validators.required],
    yearsExperience: ['', Validators.required],
    town: ['', Validators.required],
  });

  constructor(
    private collaboratorService: CollaboratorService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.collaboratorService.getCollaboratorById(params['id']).subscribe((collaborator: Collaborator) => {
          this.collaborator = collaborator;
          this.initForm();
        });
      }
    });

  }

  submitData() {
    this.collaboratorService.updateCollaboratorFromAdmin(this.getCollaboratorFromFormData()).pipe().subscribe(() => {
      window.location.reload();
    });
  }

  deleteCollaborator() {
    console.log(this.collaboratorForm);
    this.collaboratorService.deleteCollaborator(this.collaborator).pipe().subscribe(() => {
      this.router.navigate(['admin/user']);
    });

  }

  getCollaboratorFromFormData(): Collaborator {
    let email = this.collaboratorForm.get('email')?.value;
    let phone = this.collaboratorForm.get('phone')?.value;
    let yearsExperience = this.collaboratorForm.get('yearsExperience')?.value;

    if (email && phone && yearsExperience) {
      return {
        id: this.collaborator.id,
        email: email,
        firstName: this.collaboratorForm.get('firstName')?.value,
        lastName: this.collaboratorForm.get('lastName')?.value,
        phone: phone,
        age: this.collaboratorForm.get('age')?.value,
        size: this.collaboratorForm.get('size')?.value,
        yearsExperience: Number(yearsExperience),
        town: this.collaboratorForm.get('town')?.value,
      }
    }

    return {} as Collaborator;
  }

  private initForm() {
    if (this.collaborator.firstName && this.collaborator.lastName && this.collaborator.email && this.collaborator.phone && this.collaborator.age && this.collaborator.size && this.collaborator.yearsExperience && this.collaborator.town) {
      this.collaboratorForm.get('email')?.setValue(this.collaborator.email);
      this.collaboratorForm.get('countryCode')?.setValue(this.collaborator.phone.substring(0, 3));
      this.collaboratorForm.get('phone')?.setValue(this.collaborator.phone.substring(3));
      this.collaboratorForm.get('firstName')?.setValue(this.collaborator.firstName);
      this.collaboratorForm.get('lastName')?.setValue(this.collaborator.lastName);
      this.collaboratorForm.get('age')?.setValue(this.collaborator.age);
      this.collaboratorForm.get('size')?.setValue(this.collaborator.size);
      this.collaboratorForm.get('yearsExperience')?.setValue(String(this.collaborator.yearsExperience));
      this.collaboratorForm.get('town')?.setValue(this.collaborator.town);
    }
  }
}
