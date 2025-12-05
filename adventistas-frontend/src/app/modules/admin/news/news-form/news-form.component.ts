import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsService } from '../../../../core/services/news.service';
import { CategoryService } from '../../../../core/services/category.service';
import { MediaService } from '../../../../core/services/media.service';
import { Category } from '../../../../core/models/category.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.scss']
})
export class NewsFormComponent implements OnInit {
  newsForm: FormGroup;
  categories: Category[] = [];
  isEditMode = false;
  newsId: number | null = null;
  isLoading = false;
  isSubmitting = false;
  
  selectedImage: File | null = null;
  imagePreview: string | null = null;

  constructor(
    private fb: FormBuilder,
    private newsService: NewsService,
    private categoryService: CategoryService,
    private mediaService: MediaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.newsForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      slug: ['', [Validators.required]],
      summary: ['', [Validators.required, Validators.maxLength(500)]],
      content: ['', [Validators.required]],
      imageUrl: [''],
      categoryId: [null, [Validators.required]],
      tags: [''],
      isFeatured: [false],
      allowComments: [true],
      metaTitle: [''],
      metaDescription: [''],
      metaKeywords: ['']
    });
  }

  ngOnInit(): void {
    this.loadCategories();
    
    // Verificar si es modo edición
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.newsId = +params['id'];
        this.loadNews(this.newsId);
      }
    });

    // Auto-generar slug desde el título
    this.newsForm.get('title')?.valueChanges.subscribe(title => {
      if (title && !this.isEditMode) {
        const slug = this.generateSlug(title);
        this.newsForm.patchValue({ slug }, { emitEvent: false });
      }
    });
  }

  loadCategories(): void {
    this.categoryService.getActive().subscribe({
      next: (response) => {
        this.categories = response.data;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
  }

  loadNews(id: number): void {
    this.isLoading = true;
    this.newsService.getById(id).subscribe({
      next: (response) => {
        const news = response.data;
        this.newsForm.patchValue({
          title: news.title,
          slug: news.slug,
          summary: news.summary,
          content: news.content,
          imageUrl: news.imageUrl,
          categoryId: news.categoryId,
          tags: news.tags,
          isFeatured: news.isFeatured,
          allowComments: news.allowComments,
          metaTitle: news.metaTitle,
          metaDescription: news.metaDescription,
          metaKeywords: news.metaKeywords
        });
        
        if (news.imageUrl) {
          this.imagePreview = news.imageUrl;
        }
        
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading news:', error);
        this.isLoading = false;
        this.router.navigate(['/admin/news']);
      }
    });
  }

  onImageSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedImage = file;
      
      // Preview
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
    if (this.newsForm.invalid) {
      Object.keys(this.newsForm.controls).forEach(key => {
        this.newsForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isSubmitting = true;

    // Subir imagen si hay una seleccionada
    if (this.selectedImage) {
      const imageUrl = await this.uploadImage();
      if (imageUrl) {
        this.newsForm.patchValue({ imageUrl });
      }
    }

    const formData = this.newsForm.value;

    const request$ = this.isEditMode && this.newsId
      ? this.newsService.update(this.newsId, formData)
      : this.newsService.create(formData);

    request$.subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: this.isEditMode ? 'Noticia actualizada' : 'Noticia creada',
          text: 'La operación se completó exitosamente',
          confirmButtonColor: '#1e40af',
          timer: 2000
        }).then(() => {
          this.router.navigate(['/admin/news']);
        });
      },
      error: (error) => {
        console.error('Error saving news:', error);
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
    this.router.navigate(['/admin/news']);
  }
}