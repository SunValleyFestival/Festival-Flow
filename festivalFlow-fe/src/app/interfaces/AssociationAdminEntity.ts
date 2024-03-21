import {Collaborator} from "./CollaboratorEntity";

export interface AssociationAdmin {
  collaborator: Collaborator;
  shiftId: number;
  status: any;
  comment: string;
}
