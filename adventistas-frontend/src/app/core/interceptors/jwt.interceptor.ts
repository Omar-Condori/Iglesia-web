import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';
import { environment } from '../../../environments/environment';

/**
 * Interceptor que agrega el token JWT a todas las peticiones HTTP
 */
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Obtener el token
    const token = this.tokenService.getToken();
    
    // Solo agregar token si existe y la petición es hacia nuestra API
    if (token && request.url.startsWith(environment.apiUrl)) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request);
  }
}