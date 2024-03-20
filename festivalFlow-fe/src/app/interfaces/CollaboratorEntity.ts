export interface Collaborator {
  id?: number;
  email: string;
  phone?: string;
  countryCode?: string;
  firstName?: string | null;
  lastName?: string | null;
  age?: string | null;
  size?: string | null;
  yearsExperience?: number | null;
  town?: string | null;
}
