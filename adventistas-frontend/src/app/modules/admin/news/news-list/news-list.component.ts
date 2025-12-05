import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NewsService } from '../../../../core/services/news.service';
import { News } from '../../../../core/models/news.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.scss']
})
export class NewsListComponent implements OnInit {
  newsList: News[] = [];
  
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  
  isLoading = false;
  searchTerm = '';

  constructor(
    private newsService: NewsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadNews();
  }

  loadNews(): void {
    this.isLoading = true;
    this.newsService.getAll(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.newsList = response.data.content;
        this.totalPages = response.data.totalPages;
        this.totalElements = response.data.totalElements;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading news:', error);
        this.isLoading = false;
      }
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadNews();
  }

  createNews(): void {
    this.router.navigate(['/admin/news/new']);
  }

  editNews(id: number): void {
    this.router.navigate(['/admin/news/edit', id]);
  }

  publishNews(news: News): void {
    if (news.status === 'PUBLISHED') {
      Swal.fire({
        icon: 'info',
        title: 'Ya publicada',
        text: 'Esta noticia ya está publicada',
        confirmButtonColor: '#1e40af'
      });
      return;
    }

    Swal.fire({
      title: '¿Publicar noticia?',
      text: 'La noticia será visible para todos los usuarios',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#1e40af',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, publicar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.newsService.publish(news.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Publicada',
              text: 'La noticia ha sido publicada exitosamente',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadNews();
          },
          error: (error) => {
            console.error('Error publishing news:', error);
          }
        });
      }
    });
  }

  deleteNews(news: News): void {
    Swal.fire({
      title: '¿Eliminar noticia?',
      text: `¿Está seguro de eliminar "${news.title}"? Esta acción no se puede deshacer.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.newsService.delete(news.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Eliminada',
              text: 'La noticia ha sido eliminada exitosamente',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadNews();
          },
          error: (error) => {
            console.error('Error deleting news:', error);
          }
        });
      }
    });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PUBLISHED': return 'bg-green-100 text-green-800';
      case 'DRAFT': return 'bg-yellow-100 text-yellow-800';
      case 'ARCHIVED': return 'bg-gray-100 text-gray-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatusText(status: string): string {
    switch (status) {
      case 'PUBLISHED': return 'Publicada';
      case 'DRAFT': return 'Borrador';
      case 'ARCHIVED': return 'Archivada';
      default: return status;
    }
  }
}