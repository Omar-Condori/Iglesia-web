import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth.model';
import { BaseResponse } from '../models/base-response.model';
import { TokenService } from './token.service';
import { User } from '../models/user.model';

/**
 * Servicio de autenticación que maneja login, logout y estado del usuario
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  // BehaviorSubject para mantener el estado de autenticación
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.tokenService.hasToken() && !this.tokenService.isTokenExpired());
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  // BehaviorSubject para el usuario actual
  private currentUserSubject = new BehaviorSubject<User | null>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    private tokenService: TokenService
  ) {
    // Verificar token al iniciar
    this.checkTokenValidity();
  }

  /**
   * Realiza login contra el backend
   */
  login(credentials: LoginRequest): Observable<BaseResponse<LoginResponse>> {
    return this.http.post<BaseResponse<LoginResponse>>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          if (response.success && response.data) {
            // Guardar token
            this.tokenService.setToken(response.data.accessToken);

            // Actualizar estado de autenticación
            this.isAuthenticatedSubject.next(true);

            // Cargar datos del usuario
            this.loadCurrentUser();
          }
        })
      );
  }

  /**
   * Registra un nuevo usuario
   */
  register(data: any): Observable<BaseResponse<any>> {
    return this.http.post<BaseResponse<any>>(`${this.apiUrl}/register`, data);
  }

  /**
   * Cierra sesión del usuario
   */
  logout(): void {
    this.tokenService.removeToken();
    localStorage.removeItem(environment.userKey);
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  /**
   * Verifica si el usuario está autenticado
   */
  isLoggedIn(): boolean {
    return this.tokenService.hasToken() && !this.tokenService.isTokenExpired();
  }

  /**
   * Obtiene el usuario actual
   */
  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  /**
   * Obtiene el email del usuario actual
   */
  getCurrentUserEmail(): string | null {
    return this.tokenService.getUserEmail();
  }

  /**
   * Verifica si el usuario tiene un permiso específico
   */
  hasPermission(permission: string): boolean {
    return this.tokenService.hasAuthority(permission);
  }

  /**
   * Verifica si el usuario tiene alguno de los permisos especificados
   */
  hasAnyPermission(permissions: string[]): boolean {
    return this.tokenService.hasAnyAuthority(permissions);
  }

  /**
   * Carga los datos completos del usuario actual
   */
  private loadCurrentUser(): void {
    const email = this.tokenService.getUserEmail();
    if (email) {
      // Aquí podrías hacer una llamada al backend para obtener datos completos del usuario
      // Por ahora, creamos un usuario básico desde el token
      const user: User = {
        id: 0,
        email: email,
        firstName: email.split('@')[0],
        lastName: '',
        isActive: true,
        roles: [],
        createdAt: new Date().toISOString()
      };

      localStorage.setItem(environment.userKey, JSON.stringify(user));
      this.currentUserSubject.next(user);
    }
  }

  /**
   * Obtiene el usuario desde localStorage
   */
  private getUserFromStorage(): User | null {
    const userJson = localStorage.getItem(environment.userKey);
    if (userJson) {
      try {
        return JSON.parse(userJson);
      } catch (error) {
        console.error('Error parsing user from storage:', error);
        return null;
      }
    }
    return null;
  }

  /**
   * Verifica la validez del token al iniciar la aplicación
   */
  private checkTokenValidity(): void {
    if (this.tokenService.isTokenExpired()) {
      // Token expirado: limpiar sesión pero NO redirigir forzosamente
      this.tokenService.removeToken();
      localStorage.removeItem(environment.userKey);
      this.isAuthenticatedSubject.next(false);
      this.currentUserSubject.next(null);
    }
  }
}