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
  ) { }

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
    // Si tiene un enlace de Google Maps en website, abrirlo directamente
    if (church.website && this.isGoogleMapsLink(church.website)) {
      window.open(church.website, '_blank');
      return;
    }

    // Si tiene coordenadas, mostrar mapa interactivo con ubicación del usuario
    if (church.latitude && church.longitude) {
      this.showInteractiveMap(church);
    } else {
      Swal.fire({
        icon: 'info',
        title: 'Sin ubicación',
        text: 'Esta iglesia no tiene coordenadas GPS registradas',
        confirmButtonColor: '#1e40af'
      });
    }
  }

  isGoogleMapsLink(url: string): boolean {
    // Detecta todos los formatos de enlaces de Google Maps
    const mapsPatterns = [
      'maps.google.com',
      'maps.app.goo.gl',
      'goo.gl/maps',
      'google.com/maps'
    ];

    return mapsPatterns.some(pattern => url.includes(pattern));
  }

  showInteractiveMap(church: Church): void {
    // Obtener ubicación del usuario
    if (navigator.geolocation) {
      Swal.fire({
        title: 'Cargando mapa...',
        text: 'Obteniendo tu ubicación',
        allowOutsideClick: false,
        didOpen: () => {
          Swal.showLoading();
        }
      });

      navigator.geolocation.getCurrentPosition(
        (position) => {
          const userLat = position.coords.latitude;
          const userLng = position.coords.longitude;

          // Crear mapa interactivo con ambas ubicaciones
          const mapHtml = `
            <div style="width: 100%; height: 500px;">
              <iframe
                width="100%"
                height="100%"
                frameborder="0"
                style="border:0"
                referrerpolicy="no-referrer-when-downgrade"
                src="https://www.google.com/maps/embed/v1/directions?key=AIzaSyBFw0Qbyq9zTFTd-tUY6dZWTgaQzuU17R8&origin=${userLat},${userLng}&destination=${church.latitude},${church.longitude}&mode=driving"
                allowfullscreen>
              </iframe>
            </div>
          `;

          Swal.fire({
            title: `Ruta a ${church.name}`,
            html: mapHtml,
            width: '90%',
            showConfirmButton: true,
            showCancelButton: true,
            confirmButtonText: '<i class="fas fa-external-link-alt"></i> Abrir en Google Maps',
            cancelButtonText: 'Cerrar',
            confirmButtonColor: '#1e40af',
            cancelButtonColor: '#6b7280'
          }).then((result) => {
            if (result.isConfirmed) {
              window.open(`https://www.google.com/maps/dir/${userLat},${userLng}/${church.latitude},${church.longitude}`, '_blank');
            }
          });
        },
        (error) => {
          console.error('Error getting location:', error);
          // Si no se puede obtener ubicación del usuario, mostrar solo la iglesia
          Swal.fire({
            title: `Ubicación: ${church.name}`,
            html: `
              <div style="width: 100%; height: 400px;">
                <iframe
                  width="100%"
                  height="100%"
                  frameborder="0"
                  style="border:0"
                  referrerpolicy="no-referrer-when-downgrade"
                  src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBFw0Qbyq9zTFTd-tUY6dZWTgaQzuU17R8&q=${church.latitude},${church.longitude}&zoom=15"
                  allowfullscreen>
                </iframe>
              </div>
            `,
            width: '90%',
            showConfirmButton: true,
            confirmButtonText: '<i class="fas fa-external-link-alt"></i> Abrir en Google Maps',
            confirmButtonColor: '#1e40af'
          }).then((result) => {
            if (result.isConfirmed) {
              window.open(`https://www.google.com/maps?q=${church.latitude},${church.longitude}`, '_blank');
            }
          });
        }
      );
    } else {
      // Navegador no soporta geolocalización
      window.open(`https://www.google.com/maps?q=${church.latitude},${church.longitude}`, '_blank');
    }
  }
}