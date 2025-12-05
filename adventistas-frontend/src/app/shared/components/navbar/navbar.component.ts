import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { DepartmentService } from '../../../core/services/department.service';
import { AboutService } from '../../../core/services/about.service';
import { Department } from '../../../core/models/department.model';
import { AboutSection } from '../../../core/models/about.model';
import { TranslateService } from '@ngx-translate/core';

/**
 * Componente de navegación principal
 */
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isMenuOpen = false;
  isLoggedIn = false;
  departments: Department[] = [];
  aboutSections: AboutSection[] = [];
  showDepartmentsDropdown = false;

  constructor(
    public authService: AuthService,
    private router: Router,
    private translate: TranslateService,
    private departmentService: DepartmentService,
    private aboutService: AboutService
  ) { }

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(
      isAuth => this.isLoggedIn = isAuth
    );
    this.loadDepartments();
    this.loadAboutSections();
  }

  loadDepartments(): void {
    this.departmentService.getActiveDepartments().subscribe({
      next: (response) => {
        this.departments = response.data;
      },
      error: (error) => console.error('Error loading departments:', error)
    });
  }

  loadAboutSections(): void {
    this.aboutService.getActiveSections().subscribe({
      next: (response) => {
        this.aboutSections = response.data;
      },
      error: (error) => console.error('Error loading about sections:', error)
    });
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  hasAdminPermissions(): boolean {
    return this.authService.hasAnyPermission(['news.view', 'users.view', 'news.create']);
  }

  logout(): void {
    this.authService.logout();
  }

  goToAdmin(): void {
    this.router.navigate(['/admin']);
  }
}