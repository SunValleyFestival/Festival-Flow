import {Day} from "./DayEntity";

export interface Location {
  id?: number;
  name: string;
  description?: string | null;
  day: Day;
  adultsOnly?: boolean;
  manager?: string;
  image?: string;
}

export interface LocationClient {
  id?: number;
  name: string;
  description?: string | null;
  day: Day;
  adultsOnly?: boolean;
  manager?: string;
  image?: string;
  shiftAvailability: number;
}

