import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../../core/services/category.service';
import { Category } from '../../../core/models/category.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = [];
  categoryForm: FormGroup;
  isEditMode = false;
  editingId: number | null = null;
  isLoading = false;
  showForm = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      slug: ['', [Validators.required]],
      description: [''],
      color: ['#1e40af'],
      icon: [''],
      isActive: [true]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
    
    this.categoryForm.get('name')?.valueChanges.subscribe(name => {
      if (name && !this.isEditMode) {
        const slug = this.generateSlug(name);
        this.categoryForm.patchValue({ slug }, { emitEvent: false });
      }
    });
  }

  loadCategories(): void {
    this.isLoading = true;
    this.categoryService.getAll().subscribe({
      next: (response) => {
        this.categories = response.data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
        this.isLoading = false;
      }
    });
  }

  showCreateForm(): void {
    this.showForm = true;
    this.isEditMode = false;
    this.categoryForm.reset({ isActive: true, color: '#1e40af' });
  }

  editCategory(category: Category): void {
    this.showForm = true;
    this.isEditMode = true;
    this.editingId = category.id;
    this.categoryForm.patchValue(category);
  }

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      Object.keys(this.categoryForm.controls).forEach(key => {
        this.categoryForm.get(key)?.markAsTouched();
      });
      return;
    }

    const formData = this.categoryForm.value;
    const request$ = this.isEditMode && this.editingId
      ? this.categoryService.update(this.editingId, formData)
      : this.categoryService.create(formData);

    request$.subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: this.isEditMode ? 'Categoría actualizada' : 'Categoría creada',
          confirmButtonColor: '#1e40af',
          timer: 2000
        });
        this.loadCategories();
        this.cancelForm();
      },
      error: (error) => {
        console.error('Error saving category:', error);
      }
    });
  }

  deleteCategory(category: Category): void {
    Swal.fire({
      title: '¿Eliminar categoría?',
      text: `¿Está seguro de eliminar "${category.name}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoryService.delete(category.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Eliminada',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadCategories();
          }
        });
      }
    });
  }

  toggleActive(category: Category): void {
    this.categoryService.toggleActive(category.id).subscribe({
      next: () => {
        this.loadCategories();
      }
    });
  }

  cancelForm(): void {
    this.showForm = false;
    this.isEditMode = false;
    this.editingId = null;
    this.categoryForm.reset({ isActive: true, color: '#1e40af' });
  }

  generateSlug(text: string): string {
    return text.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '').replace(/[^a-z0-9]+/g, '-').replace(/^-+|-+$/g, '');
  }
}