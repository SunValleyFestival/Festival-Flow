export interface AuthEntity {
  userId: number;
  code?: string;
  token?: string;
  isValid?: boolean;
}
