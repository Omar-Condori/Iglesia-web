// Modelo de usuario
export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  phone?: string;
  avatarUrl?: string;
  isActive: boolean;
  roles: Role[];
  createdAt: string;
  updatedAt?: string;
  nombres?: string;
  apellidoPaterno?: string;
  apellidoMaterno?: string;
  miembroIglesia?: boolean;
}

export interface Role {
  id: number;
  name: string;
  slug: string;
  description?: string;
  permissions: Permission[];
}

export interface Permission {
  id: number;
  name: string;
  slug: string;
  description?: string;
  module: string;
}

export interface CreateUserRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phone?: string;
  avatarUrl?: string;
  roleIds: number[];
}

export interface RegisterRequest {
  nombres: string;
  apellidoPaterno: string;
  apellidoMaterno: string;
  email: string;
  password: string;
  miembroIglesia: boolean;
}

export interface UpdateUserRequest {
  email?: string;
  firstName?: string;
  lastName?: string;
  phone?: string;
  avatarUrl?: string;
  isActive?: boolean;
  roleIds?: number[];
}

export interface UserResponse {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  fullName: string;
  phone?: string;
  avatarUrl?: string;
  isActive: boolean;
  roles: Role[];
  roleNames: string[];
  createdAt: string;
}