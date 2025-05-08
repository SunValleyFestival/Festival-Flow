import {Component, OnInit} from '@angular/core';
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {FormBuilder, Validators} from "@angular/forms";
import {CollaboratorService} from "../../../services/http/user/collaborator.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  dataError = false;

  collaboratorForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    phone: [''],
    countryCode: [''],
    age: [''],
    size: [''],
    yearsExperience: [''],
    town: ['']
  });

  constructor(
    private fb: FormBuilder,
    private collaboratorService: CollaboratorService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  /** Ritorna true se il controllo è invalido e toccato */
  isInvalid(ctrlName: string): boolean {
    const ctrl = this.collaboratorForm.get(ctrlName);
    return !!ctrl && ctrl.invalid && ctrl.touched;
  }

  submitData(): void {
    if (this.collaboratorForm.invalid) {
      this.dataError = true;
      return;
    }

    this.dataError = false;
    const collaborator = this.getCollaboratorFromForm();

    this.collaboratorService.createCollaborator(collaborator)
      .subscribe((created: Collaborator) => {     // <--  tipo esplicito
        if (created.id != null) {
          this.router.navigate(['admin/user']);
        }
      });

  }

  /** Converte il FormGroup nell’oggetto Collaborator */
  private getCollaboratorFromForm(): Collaborator {
    const raw = this.collaboratorForm.value;

    return {
      email: raw.email!,
      firstName: raw.firstName!,
      lastName: raw.lastName!,
      phone: raw.phone ?? undefined,
      countryCode: raw.countryCode ?? undefined,
      age: raw.age ?? undefined,
      size: raw.size ?? undefined,
      yearsExperience: raw.yearsExperience !== ''
        ? Number(raw.yearsExperience)
        : undefined,
      town: raw.town ?? undefined
    };
  }

}
