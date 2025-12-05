import { Component, OnInit } from '@angular/core';
import { ChurchService } from '../../../core/services/church.service';
import { Church } from '../../../core/models/church.model';

@Component({
  selector: 'app-churches-list',
  templateUrl: './churches-list.component.html',
  styleUrls: ['./churches-list.component.scss']
})
export class ChurchesListComponent implements OnInit {
  churches: Church[] = [];
  filteredChurches: Church[] = [];
  searchTerm = '';
  selectedCity = '';
  cities: string[] = [];
  
  currentPage = 0;
  pageSize = 12;
  totalPages = 0;
  totalElements = 0;
  
  isLoading = false;

  constructor(private churchService: ChurchService) {}

  ngOnInit(): void {
    this.loadChurches();
  }

  loadChurches(): void {
    this.isLoading = true;
    this.churchService.getActive(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.churches = response.data.content;
        this.filteredChurches = this.churches;
        this.totalPages = response.data.totalPages;
        this.totalElements = response.data.totalElements;
        this.isLoading = false;
        
        // Extraer ciudades únicas
        this.cities = [...new Set(this.churches.map(c => c.city))].sort();
      },
      error: (error) => {
        console.error('Error loading churches:', error);
        this.isLoading = false;
      }
    });
  }

  onSearch(): void {
    this.filterChurches();
  }

  onCityChange(): void {
    this.filterChurches();
  }

  filterChurches(): void {
    this.filteredChurches = this.churches.filter(church => {
      const matchesSearch = church.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
                           church.address.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesCity = !this.selectedCity || church.city === this.selectedCity;
      return matchesSearch && matchesCity;
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadChurches();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  openMap(church: Church): void {
    if (church.latitude && church.longitude) {
      window.open(`https://www.google.com/maps?q=${church.latitude},${church.longitude}`, '_blank');
    }
  }
}