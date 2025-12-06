import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../../environments/environment';
import { TokenPayload } from '../models/auth.model';

/**
 * Servicio para gestionar el token JWT en localStorage
 */
@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  /**
   * Guarda el token en localStorage
   */
  setToken(token: string): void {
    localStorage.setItem(environment.tokenKey, token);
  }

  /**
   * Obtiene el token desde localStorage
   */
  getToken(): string | null {
    return localStorage.getItem(environment.tokenKey);
  }

  /**
   * Elimina el token de localStorage
   */
  removeToken(): void {
    localStorage.removeItem(environment.tokenKey);
  }

  /**
   * Verifica si hay un token guardado
   */
  hasToken(): boolean {
    return !!this.getToken();
  }

  /**
   * Decodifica el token JWT y retorna el payload
   */
  decodeToken(): TokenPayload | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      return jwtDecode<TokenPayload>(token);
    } catch (error) {
      console.error('Error decodificando token:', error);
      return null;
    }
  }

  /**
   * Verifica si el token está expirado
   */
  isTokenExpired(): boolean {
    const payload = this.decodeToken();
    if (!payload) {
      return true;
    }

    const now = Date.now() / 1000;
    return payload.exp < now;
  }

  /**
   * Obtiene el email del usuario desde el token
   */
  getUserEmail(): string | null {
    const payload = this.decodeToken();
    return payload?.sub || null;
  }

  /**
   * Obtiene los permisos/authorities del usuario desde el token
   */
  getUserAuthorities(): string[] {
    const payload = this.decodeToken();
    if (!payload?.authorities) {
      return [];
    }

    // Handle both string (comma-separated) and array formats
    if (typeof payload.authorities === 'string') {
      return payload.authorities
        .split(',')
        .map(auth => auth.trim())
        .filter(auth => auth.length > 0);
    }

    return payload.authorities;
  }

  /**
   * Verifica si el usuario tiene un permiso específico
   */
  hasAuthority(authority: string): boolean {
    const authorities = this.getUserAuthorities();
    return authorities.includes(authority);
  }

  /**
   * Verifica si el usuario tiene alguno de los permisos especificados
   */
  hasAnyAuthority(authorities: string[]): boolean {
    const userAuthorities = this.getUserAuthorities();
    return authorities.some(auth => userAuthorities.includes(auth));
  }
}