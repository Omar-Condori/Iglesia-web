import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RegisterRequest } from '../../../core/models/user.model';
import Swal from 'sweetalert2';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
    registerForm: FormGroup;
    isLoading = false;
    submitted = false;
    showPassword = false;
    showConfirmPassword = false;

    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private router: Router
    ) {
        this.registerForm = this.formBuilder.group({
            nombres: ['', [Validators.required]],
            apellidoPaterno: ['', [Validators.required]],
            apellidoMaterno: ['', [Validators.required]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            confirmarPassword: ['', [Validators.required]],
            miembroIglesia: [false]
        }, { validators: this.passwordMatchValidator });
    }

    // Custom validator for password matching
    passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
        const password = control.get('password');
        const confirmPassword = control.get('confirmarPassword');

        if (password && confirmPassword && password.value !== confirmPassword.value) {
            confirmPassword.setErrors({ passwordMismatch: true });
            return { passwordMismatch: true };
        } else {
            return null;
        }
    }

    get f() { return this.registerForm.controls; }

    togglePasswordVisibility(): void {
        this.showPassword = !this.showPassword;
    }

    toggleConfirmPasswordVisibility(): void {
        this.showConfirmPassword = !this.showConfirmPassword;
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.registerForm.invalid) {
            return;
        }

        this.isLoading = true;

        const request: RegisterRequest = {
            nombres: this.f['nombres'].value,
            apellidoPaterno: this.f['apellidoPaterno'].value,
            apellidoMaterno: this.f['apellidoMaterno'].value,
            email: this.f['email'].value,
            password: this.f['password'].value,
            miembroIglesia: this.f['miembroIglesia'].value
        };

        this.authService.register(request).subscribe({
            next: (response) => {
                this.isLoading = false;
                Swal.fire({
                    icon: 'success',
                    title: 'Registro exitoso',
                    text: 'Tu cuenta ha sido creada correctamente. Por favor inicia sesión.',
                    confirmButtonColor: '#1e40af'
                }).then(() => {
                    this.router.navigate(['/login']);
                });
            },
            error: (error) => {
                this.isLoading = false;
                console.error('Registration error:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error en el registro',
                    text: error.error?.message || 'Ha ocurrido un error al intentar registrarte. Por favor intenta nuevamente.',
                    confirmButtonColor: '#ef4444'
                });
            }
        });
    }
}
