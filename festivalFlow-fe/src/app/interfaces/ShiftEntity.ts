import {Location} from './LocationEntity';
import {Collaborator} from "./CollaboratorEntity";

export interface Shift {
  id?: number;
  description?: string | null;
  name: string;
  location: Location;
  startTime: string;
  endTime: string;
  maxCollaborator: number;
  collaboratorName?: string[],
  collaboratorEntityList?: Collaborator[]
}
