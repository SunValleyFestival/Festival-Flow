import {Location} from './LocationEntity';

export interface Shift {
  id?: number;
  description?: string | null;
  name: string;
  location: Location;
  startTime: string;
  endTime: string;
  day: number;
  maxCollaborator: number;
}
