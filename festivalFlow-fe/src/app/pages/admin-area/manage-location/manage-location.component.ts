import {Component, OnInit} from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {ShiftService} from "../../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Association} from "../../../interfaces/AssociationEntity";
import {AssociationService} from "../../../services/http/association.service";
import {AssociationAdmin} from "../../../interfaces/AssociationAdminEntity";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-manage-location',
  templateUrl: './manage-location.component.html',
  styleUrls: ['./manage-location.component.css']
})
export class ManageLocationComponent implements OnInit {
  protected dataError: boolean = false;
  protected locationName: string = '';
  protected shifts: Shift[] = [];

  protected locationId!: number;
  protected adminAssociations: AssociationAdmin[] = [];

  protected shiftForm = this.fb.group({
    name: ['', [Validators.required]],
    description: ['', Validators.required],
    startTime: ['', Validators.required],
    endTime: ['', Validators.required],
    maxCollaborator: ['', Validators.required]
  });

  constructor(
    private shiftService: ShiftService,
    protected associationService: AssociationService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['location']) {
        this.locationId = params['location'];

        this.shiftService.getShiftsByLocationId(this.locationId).subscribe((shifts: Shift[]) => {
          this.shifts = shifts;
        });
      }
      if (params['name']) {
        this.locationName = params['name'];
      }

    });
  }

  submitData() {
    this.shiftService.createShift(this.getShiftFromFormData()).pipe().subscribe(() => {
      window.location.reload();
    });

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


  getSelectedShiftAssociations(shiftId: number | undefined) {
    if (shiftId !== undefined) {
      this.associationService.getAdminAssociationByShiftId(shiftId).pipe().subscribe((associations: AssociationAdmin[]) => {
        this.adminAssociations = associations;
      });
    }
  }

  getShiftFromFormData(): Shift {
    let name = this.shiftForm.get('name')?.value;
    let description = this.shiftForm.get('description')?.value;
    let startTime = this.shiftForm.get('startTime')?.value;
    let endTime = this.shiftForm.get('endTime')?.value;
    let maxCollaborator = this.shiftForm.get('maxCollaborator')?.value;

    if (name && description && startTime && endTime && maxCollaborator && this.locationId) {
      return {
        name: name,
        description: description,
        startTime: startTime,
        endTime: endTime,
        maxCollaborator: Number(maxCollaborator),
        location: {
          id: this.locationId
        },
      } as Shift;
    }
    return {} as Shift;
  }

  formShow() {
    console.log(this.shiftForm)
  }
}
