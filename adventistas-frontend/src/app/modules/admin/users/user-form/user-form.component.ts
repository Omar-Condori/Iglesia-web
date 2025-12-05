import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../../core/services/user.service';
import { CreateUserRequest, UpdateUserRequest } from '../../../../core/models/user.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  isEditMode = false;
  userId?: number;
  isLoading = false;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.userId = +params['id'];
        this.loadUser();
      }
    });
  }

  initForm(): void {
    this.userForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', this.isEditMode ? [] : [Validators.required, Validators.minLength(6)]],
      isActive: [true]
    });
  }

  loadUser(): void {
    if (!this.userId) return;

    this.isLoading = true;
    this.userService.getById(this.userId).subscribe({
      next: (response) => {
        const user = response.data;
        this.userForm.patchValue({
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          isActive: user.isActive
        });
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo cargar el usuario'
        });
        this.router.navigate(['/admin/users']);
      }
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      Object.keys(this.userForm.controls).forEach(key => {
        this.userForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isLoading = true;

    if (this.isEditMode && this.userId) {
      const updateRequest: UpdateUserRequest = {
        firstName: this.userForm.value.firstName,
        lastName: this.userForm.value.lastName,
        email: this.userForm.value.email,
        isActive: this.userForm.value.isActive
      };

      if (this.userForm.value.password) {
        (updateRequest as any).password = this.userForm.value.password;
      }

      this.userService.update(this.userId, updateRequest).subscribe({
        next: () => {
          this.isLoading = false;
          Swal.fire({
            icon: 'success',
            title: 'Usuario actualizado',
            timer: 1500,
            showConfirmButton: false
          });
          this.router.navigate(['/admin/users']);
        },
        error: () => {
          this.isLoading = false;
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo actualizar el usuario'
          });
        }
      });
    } else {
      const createRequest: CreateUserRequest = this.userForm.value;

      this.userService.create(createRequest).subscribe({
        next: () => {
          this.isLoading = false;
          Swal.fire({
            icon: 'success',
            title: 'Usuario creado',
            timer: 1500,
            showConfirmButton: false
          });
          this.router.navigate(['/admin/users']);
        },
        error: (error) => {
          this.isLoading = false;
          const message = error.error?.message || 'No se pudo crear el usuario';
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: message
          });
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/admin/users']);
  }

  hasError(field: string, error: string): boolean {
    const control = this.userForm.get(field);
    return !!(control && control.hasError(error) && control.touched);
  }
}
