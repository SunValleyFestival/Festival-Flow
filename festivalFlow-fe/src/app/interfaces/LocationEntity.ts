import {Day} from "./DayEntity";

export interface Location {
  id?: number;
  name: string;
  description?: string | null;
  day: Day;
  manager?: string;
}

export interface LocationClient {
  id?: number;
  name: string;
  description?: string | null;
  day: Day;
  shiftAvailability: number;
  manager?: string;
}

