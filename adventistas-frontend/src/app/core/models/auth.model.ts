// Modelos de autenticación
export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
}

export interface TokenPayload {
  sub: string; // email
  authorities: string | string[]; // Can be comma-separated string OR array
  iat: number;
  exp: number;
}