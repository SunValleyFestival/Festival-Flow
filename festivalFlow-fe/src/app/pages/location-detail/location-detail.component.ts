import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift} from "../../interfaces/ShiftEntity";
import {AssociationService} from "../../services/http/association.service";
import {CollaboratorService} from "../../services/http/collaborator.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedDay: number | undefined;
  protected selectedLocation: number | undefined;
  protected signedIn: boolean = false;
  protected shifts: Shift[] | undefined;

  formData: Collaborator = {
    phone: '',
    firstName: '',
    lastName: '',
    age: '',
    size: 'Taglia Maglietta'
  }

  constructor(private shiftService: ShiftService, private route: ActivatedRoute, private associationService: AssociationService, private collaboratorService: CollaboratorService) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.selectedLocation = params['location'];
      this.selectedDay = params['day'];
    });

    this.shiftService.getShiftByLocationIdAndDay(this.selectedLocation, this.selectedDay).pipe().subscribe((shifts: any) => {
      this.shifts = shifts;
    })
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
      phone: '',
      firstName: '',
      lastName: '',
      age: '',
      size: 'Taglia Maglietta'
    }
  }
}
