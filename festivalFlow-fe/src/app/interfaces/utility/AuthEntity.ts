export interface AuthEntity {
    userId?: number;
    email?: string;
    code?: string;
    token?: string;
    emailSent?: boolean;
    isValid?: boolean;
}
