import {Component, OnInit} from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {Location} from "../../../interfaces/LocationEntity";
import {timer} from "rxjs";
import {ShiftService} from "../../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {CollaboratorService} from "../../../services/http/collaborator.service";
import {Association} from "../../../interfaces/AssociationEntity";
import {AssociationService} from "../../../services/http/association.service";

@Component({
  selector: 'app-manage-location',
  templateUrl: './manage-location.component.html',
  styleUrls: ['./manage-location.component.css']
})
export class ManageLocationComponent implements OnInit {
  protected dataError: boolean = false;
  protected locationName: string = '';
  protected shifts: Shift[] = [];

  formData: Shift = {
    name: '',
    description: '',
    location: {} as Location,
    startTime: '',
    endTime: '',
    maxCollaborator: 0
  }

  constructor(
    private shiftService: ShiftService,
    protected associationService: AssociationService,
    private route: ActivatedRoute,
    protected collaboratorService: CollaboratorService
  ) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['location']) {
        this.formData.location.id = params['location'];

        this.shiftService.getAdminShiftsByLocationId(params['location']).subscribe((shifts: Shift[]) => {
          this.shifts = shifts;
        });
      }
      if (params['name']) {
        this.locationName = params['name'];
      }

    });
  }

  submitData() {
    if (this.checkData()) {
      return;
    }

    this.shiftService.createShift(this.formData).pipe().subscribe();
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

  deleteShift(shift: Shift | undefined) {
    console.log('delete shift');
    if (shift !== undefined) {
      this.shiftService.deleteShift(shift).pipe().subscribe();
    }
  }

  rejectAssociation(collaborator_id: number | undefined, shift_id: number | undefined) {
    if (collaborator_id !== undefined && shift_id !== undefined) {
      let association: Association = {
        id: {
          collaboratorId: collaborator_id,
          shiftId: shift_id
        },
      }
      this.associationService.rejectAssociation(association).pipe().subscribe();
    }
  }

  approveAssociation(collaborator_id: number | undefined, shift_id: number | undefined) {
    if (collaborator_id !== undefined && shift_id !== undefined) {
      let association: Association = {
        id: {
          collaboratorId: collaborator_id,
          shiftId: shift_id
        },
        status: 0
      }

      this.associationService.approveAssociation(association).pipe().subscribe();
    }
  }
}
