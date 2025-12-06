import { Component, OnInit } from '@angular/core';
import { PrayerRequestService } from '../../../core/services/prayer-request.service';
import { PrayerRequest, RequestStatus, STATUS_LABELS, URGENCY_LABELS, PREFERENCE_TYPE_LABELS } from '../../../core/models/prayer-request.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prayer-requests',
  templateUrl: './prayer-requests.component.html',
  styleUrls: ['./prayer-requests.component.scss']
})
export class PrayerRequestsComponent implements OnInit {
  requests: PrayerRequest[] = [];
  filteredRequests: PrayerRequest[] = [];
  isLoading = false;
  statusFilter: RequestStatus | 'ALL' = 'ALL';

  statusLabels = STATUS_LABELS;
  urgencyLabels = URGENCY_LABELS;
  preferenceLabels = PREFERENCE_TYPE_LABELS;

  RequestStatus = RequestStatus;
  allStatuses = Object.values(RequestStatus);

  stats = {
    pending: 0,
    inProgress: 0,
    completed: 0
  };

  constructor(private prayerRequestService: PrayerRequestService) { }

  ngOnInit(): void {
    this.loadRequests();
    this.loadStats();
  }

  loadRequests(): void {
    this.isLoading = true;
    this.prayerRequestService.getAll(0, 100).subscribe({
      next: (response) => {
        this.requests = response.data.content;
        this.applyFilter();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading requests:', error);
        this.isLoading = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron cargar las peticiones',
          confirmButtonColor: '#1e40af'
        });
      }
    });
  }

  loadStats(): void {
    this.prayerRequestService.countByStatus(RequestStatus.PENDING).subscribe({
      next: (response) => this.stats.pending = response.data
    });
    this.prayerRequestService.countByStatus(RequestStatus.IN_PROGRESS).subscribe({
      next: (response) => this.stats.inProgress = response.data
    });
    this.prayerRequestService.countByStatus(RequestStatus.COMPLETED).subscribe({
      next: (response) => this.stats.completed = response.data
    });
  }

  applyFilter(): void {
    if (this.statusFilter === 'ALL') {
      this.filteredRequests = this.requests;
    } else {
      this.filteredRequests = this.requests.filter(r => r.status === this.statusFilter);
    }
  }

  onFilterChange(): void {
    this.applyFilter();
  }

  updateStatus(request: PrayerRequest, newStatus: RequestStatus): void {
    Swal.fire({
      title: '¿Actualizar estado?',
      text: `Cambiar a: ${this.statusLabels[newStatus]}`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#1e40af',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, actualizar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prayerRequestService.updateStatus(request.id, newStatus).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: '¡Actualizado!',
              text: 'El estado ha sido actualizado',
              timer: 2000,
              confirmButtonColor: '#1e40af'
            });
            this.loadRequests();
            this.loadStats();
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo actualizar el estado',
              confirmButtonColor: '#1e40af'
            });
          }
        });
      }
    });
  }

  deleteRequest(request: PrayerRequest): void {
    Swal.fire({
      title: '¿Eliminar petición?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prayerRequestService.delete(request.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: '¡Eliminada!',
              text: 'La petición ha sido eliminada',
              timer: 2000,
              confirmButtonColor: '#1e40af'
            });
            this.loadRequests();
            this.loadStats();
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se pudo eliminar la petición',
              confirmButtonColor: '#1e40af'
            });
          }
        });
      }
    });
  }

  getStatusBadgeClass(status: RequestStatus): string {
    const classes: Record<RequestStatus, string> = {
      [RequestStatus.PENDING]: 'bg-yellow-100 text-yellow-800',
      [RequestStatus.IN_PROGRESS]: 'bg-blue-100 text-blue-800',
      [RequestStatus.COMPLETED]: 'bg-green-100 text-green-800',
      [RequestStatus.CANCELLED]: 'bg-gray-100 text-gray-800'
    };
    return classes[status];
  }

  getUrgencyBadgeClass(urgency: string): string {
    const classes: Record<string, string> = {
      'HIGH': 'bg-red-100 text-red-800',
      'MEDIUM': 'bg-orange-100 text-orange-800',
      'LOW': 'bg-blue-100 text-blue-800'
    };
    return classes[urgency] || 'bg-gray-100 text-gray-800';
  }
}
