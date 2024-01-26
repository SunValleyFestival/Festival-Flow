export interface Shift {
  id?: number;
  description?: string | null;
  name: string;
  location_id?: number | null;
  time: string;
  day: number;
  maxCollaborator: number;
}
