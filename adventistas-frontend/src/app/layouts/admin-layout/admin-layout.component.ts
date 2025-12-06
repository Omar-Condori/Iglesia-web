import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

/**
 * Layout para panel administrativo
 */
@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent implements OnInit {
  isSidebarOpen = true;
  currentUser: any;

  menuItems = [
    { icon: 'fa-tachometer-alt', label: 'Dashboard', route: '/admin/dashboard' },
    { icon: 'fa-newspaper', label: 'Noticias', route: '/admin/news' },
    { icon: 'fa-building', label: 'Departamentos', route: '/admin/departments' },
    { icon: 'fa-praying-hands', label: 'Peticiones de Oración', route: '/admin/prayer-requests', permission: 'prayer-requests.view' },
    { icon: 'fa-tags', label: 'Categorías', route: '/admin/categories' },
    { icon: 'fa-church', label: 'Iglesias', route: '/admin/churches' },
    { icon: 'fa-users', label: 'Uniones', route: '/admin/unions' },
    { icon: 'fa-graduation-cap', label: 'Cursos', route: '/admin/courses' },
    { icon: 'fa-download', label: 'Descargas', route: '/admin/downloads' },
    { icon: 'fa-video', label: 'Videos', route: '/admin/videos' },
    { icon: 'fa-users-cog', label: 'Usuarios', route: '/admin/users', permission: 'MANAGE_USERS' }
  ];

  constructor(
    public authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(
      user => this.currentUser = user
    );
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  logout(): void {
    this.authService.logout();
  }

  hasPermission(permission?: string): boolean {
    if (!permission) return true;
    return this.authService.hasPermission(permission);
  }
}