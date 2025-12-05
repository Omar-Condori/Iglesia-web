import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChurchService } from '../../../../core/services/church.service';
import { Church } from '../../../../core/models/church.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-churches-list',
  templateUrl: './churches-list.component.html',
  styleUrls: ['./churches-list.component.scss']
})
export class ChurchesListComponent implements OnInit {
  churches: Church[] = [];
  
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  
  isLoading = false;
  searchTerm = '';

  constructor(
    private churchService: ChurchService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadChurches();
  }

  loadChurches(): void {
    this.isLoading = true;
    this.churchService.getAll(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.churches = response.data.content;
        this.totalPages = response.data.totalPages;
        this.totalElements = response.data.totalElements;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading churches:', error);
        this.isLoading = false;
      }
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadChurches();
  }

  createChurch(): void {
    this.router.navigate(['/admin/churches/new']);
  }

  editChurch(id: number): void {
    this.router.navigate(['/admin/churches/edit', id]);
  }

  deleteChurch(church: Church): void {
    Swal.fire({
      title: '¿Eliminar iglesia?',
      text: `¿Está seguro de eliminar "${church.name}"? Esta acción no se puede deshacer.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.churchService.delete(church.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Eliminada',
              text: 'La iglesia ha sido eliminada exitosamente',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadChurches();
          },
          error: (error) => {
            console.error('Error deleting church:', error);
          }
        });
      }
    });
  }

  viewOnMap(church: Church): void {
    if (church.latitude && church.longitude) {
      window.open(`https://www.google.com/maps?q=${church.latitude},${church.longitude}`, '_blank');
    }
  }
}