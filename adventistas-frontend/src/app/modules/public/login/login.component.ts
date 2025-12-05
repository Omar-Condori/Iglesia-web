import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

/**
 * Componente de inicio de sesión
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading = false;
  returnUrl = '/admin';
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Si ya está logueado, redirigir según permisos
    if (this.authService.isLoggedIn()) {
      if (this.authService.hasAnyPermission(['news.view', 'users.view'])) {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/']);
      }
      return;
    }

    // Obtener returnUrl de query params, por defecto home
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      Object.keys(this.loginForm.controls).forEach(key => {
        this.loginForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isLoading = true;
    const { email, password } = this.loginForm.value;

    this.authService.login({ email, password }).subscribe({
      next: () => {
        // Redirigir según permisos del usuario
        if (this.authService.hasAnyPermission(['news.view', 'users.view'])) {
          // Usuario con permisos de admin
          this.router.navigate([this.returnUrl.startsWith('/admin') ? this.returnUrl : '/admin']);
        } else {
          // Usuario regular - redirigir a home
          this.router.navigate(['/']);
        }
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
}