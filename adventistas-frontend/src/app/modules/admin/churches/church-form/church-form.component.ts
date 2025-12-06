import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChurchService } from '../../../../core/services/church.service';
import { MediaService } from '../../../../core/services/media.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-church-form',
  templateUrl: './church-form.component.html',
  styleUrls: ['./church-form.component.scss']
})
export class ChurchFormComponent implements OnInit {
  churchForm: FormGroup;
  isEditMode = false;
  churchId: number | null = null;
  isLoading = false;
  isSubmitting = false;
  
  selectedImage: File | null = null;
  imagePreview: string | null = null;

  constructor(
    private fb: FormBuilder,
    private churchService: ChurchService,
    private mediaService: MediaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.churchForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      slug: ['', [Validators.required]],
      description: [''],
      address: ['', [Validators.required]],
      city: ['', [Validators.required]],
      state: [''],
      country: ['Perú', [Validators.required]],
      postalCode: [''],
      latitude: [null],
      longitude: [null],
      phone: [''],
      email: ['', [Validators.email]],
      website: [''],
      serviceSchedule: [''],
      pastor: [''],
      foundedYear: [null],
      membersCount: [null],
      imageUrl: [''],
      isActive: [true],
      unionId: [null]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.churchId = +params['id'];
        this.loadChurch(this.churchId);
      }
    });

    // Auto-generar slug
    this.churchForm.get('name')?.valueChanges.subscribe(name => {
      if (name && !this.isEditMode) {
        const slug = this.generateSlug(name);
        this.churchForm.patchValue({ slug }, { emitEvent: false });
      }
    });
  }

  loadChurch(id: number): void {
    this.isLoading = true;
    this.churchService.getById(id).subscribe({
      next: (response) => {
        const church = response.data;
        this.churchForm.patchValue(church);
        
        if (church.imageUrl) {
          this.imagePreview = church.imageUrl;
        }
        
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading church:', error);
        this.isLoading = false;
        this.router.navigate(['/admin/churches']);
      }
    });
  }

  onImageSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedImage = file;
      
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreview = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  async uploadImage(): Promise<string | null> {
    if (!this.selectedImage) return null;

    try {
      const response = await this.mediaService.uploadFile(this.selectedImage, 'image').toPromise();
      return response.data.url;
    } catch (error) {
      console.error('Error uploading image:', error);
      return null;
    }
  }

  async onSubmit(): Promise<void> {
    if (this.churchForm.invalid) {
      Object.keys(this.churchForm.controls).forEach(key => {
        this.churchForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isSubmitting = true;

    // COMMENTED OUT: Media upload endpoint doesn't exist yet
    // if (this.selectedImage) {
    //   const imageUrl = await this.uploadImage();
    //   if (imageUrl) {
    //     this.churchForm.patchValue({ imageUrl });
    //   }
    // }

    const formData = this.churchForm.value;

    const request$ = this.isEditMode && this.churchId
      ? this.churchService.update(this.churchId, formData)
      : this.churchService.create(formData);

    request$.subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: this.isEditMode ? 'Iglesia actualizada' : 'Iglesia creada',
          text: 'La operación se completó exitosamente',
          confirmButtonColor: '#1e40af',
          timer: 2000
        }).then(() => {
          this.router.navigate(['/admin/churches']);
        });
      },
      error: (error) => {
        console.error('Error saving church:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || 'No se pudo guardar la iglesia',
          confirmButtonColor: '#dc2626'
        });
        this.isSubmitting = false;
      }
    });
  }

  generateSlug(text: string): string {
    return text
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/^-+|-+$/g, '');
  }

  cancel(): void {
    this.router.navigate(['/admin/churches']);
  }
}