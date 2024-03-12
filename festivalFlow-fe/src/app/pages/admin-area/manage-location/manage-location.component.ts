import {Component, OnInit} from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {Location} from "../../../interfaces/LocationEntity";
import {timer} from "rxjs";
import {ShiftService} from "../../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {CollaboratorService} from "../../../services/http/collaborator.service";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
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
  protected associations: Association[] = [];
  protected collaborators: Collaborator[] = [];

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
        this.shiftService.getShiftsByLocationId(params['location']).subscribe((shifts: Shift[]) => {
          this.shifts = shifts;

          this.shifts.forEach((shift) => {
            this.associationService.getAssociationByShiftId(shift.id).subscribe((associations: Association[]) => {
              this.associations = associations;
            });
          });

        });
      }
      if (params['name']) {
        this.locationName = params['name'];
      }

    });

    this.collaboratorService.getCollaborators().subscribe((collaborators: any[]) => {
      this.collaborators = collaborators;
    });
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

  rejectAssociation(id: number | undefined, id2: number | undefined) {

  }

  approveAssociation(id: number | undefined, id2: number | undefined) {

  }
}
