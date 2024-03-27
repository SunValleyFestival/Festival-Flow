import {Collaborator} from "./CollaboratorEntity";
import {Shift} from "./ShiftEntity";

export interface Association {
  id: {
    collaboratorId: number;
    shiftId: number;
  }
  status?: any;
  comment?: string;
}

export interface AssociationAdmin {
  collaborator: Collaborator;
  shiftId: number;
  status: any;
  comment: string;
}

export interface AssociationClient {
  collaboratorId: number;
  status?: any;
  shift: Shift,
}
