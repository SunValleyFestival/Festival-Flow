import {Day} from "./DayEntity";

export interface Location {
  id?: number;
  name: string;
  description?: string | null;
  day: Day;
}
