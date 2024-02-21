import {Location} from './LocationEntity';

export interface Shift {
  id?: number;
  description?: string | null;
  name: string;
  location: Location;
  time: string;
  day: number;
  maxCollaborator: number;
}
