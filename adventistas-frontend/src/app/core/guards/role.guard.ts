import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

/**
 * Guard que protege rutas basadas en permisos/authorities
 */
@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Obtener los permisos requeridos desde la data de la ruta
    const requiredPermissions = route.data['permissions'] as string[];

    if (!requiredPermissions || requiredPermissions.length === 0) {
      return true;
    }

    // Verificar si el usuario tiene alguno de los permisos requeridos
    if (this.authService.hasAnyPermission(requiredPermissions)) {
      return true;
    }

    // No tiene permisos - mostrar mensaje y redirigir
    Swal.fire({
      icon: 'warning',
      title: 'Acceso Denegado',
      text: 'No tiene permisos para acceder a esta sección.',
      confirmButtonColor: '#1e40af'
    });

    this.router.navigate(['/']);
    return false;
  }
}