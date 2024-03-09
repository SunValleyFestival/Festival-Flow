import { Component } from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {Location} from "../../../interfaces/LocationEntity";

@Component({
  selector: 'app-manage-location',
  templateUrl: './manage-location.component.html',
  styleUrls: ['./manage-location.component.css']
})
export class ManageLocationComponent {

  //currentLcoation: Location;

  formData: Shift = {
    name: '',
    description: '',
    location: {} as Location,
    startTime: '',
    endTime: '',
    maxCollaborator: 0
  }

  submitData() {

  }

  deleteShift() {
    console.log('delete shift');
  }
}
