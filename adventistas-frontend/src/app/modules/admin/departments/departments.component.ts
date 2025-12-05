import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DepartmentService } from '../../../core/services/department.service';
import { Department } from '../../../core/models/department.model';
import { BaseResponse } from '../../../core/models/base-response.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-departments',
  templateUrl: './departments.component.html',
  styleUrls: ['./departments.component.scss']
})
export class DepartmentsComponent implements OnInit {
  departments: Department[] = [];
  departmentForm: FormGroup;
  isEditMode = false;
  editingId: number | null = null;
  isLoading = false;
  showForm = false;

  constructor(
    private fb: FormBuilder,
    private departmentService: DepartmentService
  ) {
    this.departmentForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      icon: [''],
      color: ['#1e40af'],
      isActive: [true]
    });
  }

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.isLoading = true;
    this.departmentService.getAllDepartments().subscribe({
      next: (response: BaseResponse<Department[]>) => {
        this.departments = response.data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading departments:', error);
        this.isLoading = false;
      }
    });
  }

  showCreateForm(): void {
    this.showForm = true;
    this.isEditMode = false;
    this.departmentForm.reset({ isActive: true, color: '#1e40af' });
  }

  editDepartment(department: Department): void {
    this.showForm = true;
    this.isEditMode = true;
    this.editingId = department.id;
    this.departmentForm.patchValue(department);
  }

  onSubmit(): void {
    if (this.departmentForm.invalid) {
      Object.keys(this.departmentForm.controls).forEach(key => {
        this.departmentForm.get(key)?.markAsTouched();
      });
      return;
    }

    const formData = this.departmentForm.value;
    const request$ = this.isEditMode && this.editingId
      ? this.departmentService.update(this.editingId, formData)
      : this.departmentService.create(formData);

    request$.subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: this.isEditMode ? 'Departamento actualizado' : 'Departamento creado',
          confirmButtonColor: '#1e40af',
          timer: 2000
        });
        this.loadDepartments();
        this.cancelForm();
      },
      error: (error) => {
        console.error('Error saving department:', error);
      }
    });
  }

  deleteDepartment(department: Department): void {
    Swal.fire({
      title: '¿Eliminar departamento?',
      text: `¿Está seguro de eliminar "${department.name}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.departmentService.delete(department.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Eliminado',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadDepartments();
          }
        });
      }
    });
  }

  toggleActive(department: Department): void {
    this.departmentService.toggleActive(department.id).subscribe({
      next: () => {
        this.loadDepartments();
      }
    });
  }

  cancelForm(): void {
    this.showForm = false;
    this.isEditMode = false;
    this.editingId = null;
    this.departmentForm.reset({ isActive: true, color: '#1e40af' });
  }
}