import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

/**
 * Interceptor que maneja errores HTTP globalmente
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Ha ocurrido un error';

        if (error.error instanceof ErrorEvent) {
          // Error del cliente
          errorMessage = `Error: ${error.error.message}`;
        } else {
          // Error del servidor
          switch (error.status) {
            case 401:
              // No autorizado - redirigir al login
              errorMessage = 'Sesión expirada. Por favor, inicie sesión nuevamente.';
              this.authService.logout();
              break;
            case 403:
              // Prohibido
              errorMessage = 'No tiene permisos para realizar esta acción.';
              break;
            case 404:
              // No encontrado
              errorMessage = error.error?.message || 'Recurso no encontrado.';
              break;
            case 400:
              // Bad request
              errorMessage = error.error?.message || 'Datos inválidos.';
              break;
            case 500:
              // Error del servidor
              errorMessage = 'Error interno del servidor. Por favor, intente más tarde.';
              break;
            default:
              errorMessage = error.error?.message || `Error ${error.status}: ${error.statusText}`;
          }
        }

        // Mostrar mensaje de error con SweetAlert2
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: errorMessage,
          confirmButtonColor: '#1e40af'
        });

        return throwError(() => error);
      })
    );
  }
}