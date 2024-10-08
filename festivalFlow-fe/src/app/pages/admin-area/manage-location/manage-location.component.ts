import {Component, OnInit} from '@angular/core';
import {Shift} from "../../../interfaces/ShiftEntity";
import {ShiftService} from "../../../services/http/admin/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Association, AssociationAdmin} from "../../../interfaces/AssociationEntity";
import {AssociationService} from "../../../services/http/admin/association.service";
import {FormBuilder, Validators} from "@angular/forms";
import {LocationService} from "../../../services/http/admin/location.service";
import {Location} from "../../../interfaces/LocationEntity";

@Component({
  selector: 'app-manage-location',
  templateUrl: './manage-location.component.html',
  styleUrls: ['./manage-location.component.css']
})
export class ManageLocationComponent implements OnInit {
  protected shifts: Shift[] = [];
  protected locationId!: number;
  protected adminAssociations: AssociationAdmin[] = [];
  protected location!: Location;
  protected shiftToDel: Shift | undefined;


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
    private fb: FormBuilder,
    private locationService: LocationService
  ) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['location']) {
        this.locationId = params['location'];
        this.locationService.getLocationById(this.locationId).subscribe((location) => {
          this.location = location;
        });


        this.shiftService.getShiftsByLocationId(this.locationId).subscribe((shifts: Shift[]) => {
          this.shifts = shifts;
        });
      }
    });
  }

  submitData() {
    this.shiftService.createShift(this.getShiftFromFormData()).pipe().subscribe(() => {
      this.ngOnInit();
    });
  }

  openDeleteModal(shift: Shift) {
    this.shiftToDel = shift;
    const dialog = document.getElementById('deleteDialog') as HTMLDialogElement;
    if (dialog) {
      dialog.showModal();
    }
  }

  undoDelete() {
    this.shiftToDel = undefined;
  }

  deleteShift() {
    console.log('delete shift');
    if (this.shiftToDel !== undefined) {
      this.shiftService.deleteShift(this.shiftToDel).pipe().subscribe(() => {
        window.location.reload();
      });
    }
    this.shiftToDel = undefined;
  }

  rejectAssociation(collaborator_id: number | undefined, shift_id: number | undefined) {
    if (collaborator_id !== undefined && shift_id !== undefined) {
      let association: Association = {
        id: {
          collaboratorId: collaborator_id,
          shiftId: shift_id
        },
      }
      this.associationService.rejectAssociation(association).pipe().subscribe(() => this.ngOnInit());
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

      this.associationService.approveAssociation(association).pipe().subscribe(() => this.ngOnInit());
    }
  }


  getSelectedShiftAssociations(shiftId: number | undefined) {
    if (shiftId !== undefined) {
      this.associationService.getAdminAssociationByShiftId(shiftId).pipe().subscribe((associations: AssociationAdmin[]) => {
        this.adminAssociations = associations.filter(association => association.status !== 'REJECTED');
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

  reloadManager() {
    this.locationService.updateLocation(this.location).pipe().subscribe(() => {
      this.ngOnInit()
    });
  }
}
