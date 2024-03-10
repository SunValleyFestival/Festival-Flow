import { Component } from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {Location} from "../../../interfaces/LocationEntity";
import {timer} from "rxjs";

@Component({
  selector: 'app-manage-location',
  templateUrl: './manage-location.component.html',
  styleUrls: ['./manage-location.component.css']
})
export class ManageLocationComponent {

  protected dataError: boolean = false;

  formData: Shift = {
    name: '',
    description: '',
    location: {} as Location,
    startTime: '',
    endTime: '',
    maxCollaborator: 0
  }

  submitData() {
    this.checkData();
  }

  checkData(): boolean {
    let error = this.formData.name === '' || this.formData.description === '' || this.formData.startTime === '' || this.formData.endTime === '' || this.formData.maxCollaborator === 0;

    this.dataError = error;

    if (error) {
      timer(5000).subscribe(() => {
        this.dataError = false;
      });
    }

    return error;
  }
  deleteShift() {
    console.log('delete shift');
  }
}
