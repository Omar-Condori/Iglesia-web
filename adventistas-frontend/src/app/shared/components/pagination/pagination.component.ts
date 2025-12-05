import { Component, Input, Output, EventEmitter } from '@angular/core';

/**
 * Componente de paginación reutilizable
 */
@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent {
  protected Math = Math;
  @Input() currentPage = 0;
  @Input() totalPages = 0;
  @Input() totalElements = 0;
  @Input() pageSize = 10;
  @Output() pageChange = new EventEmitter<number>();

  get pages(): number[] {
    const pages: number[] = [];
    const maxPages = 5;

    let startPage = Math.max(0, this.currentPage - Math.floor(maxPages / 2));
    let endPage = Math.min(this.totalPages - 1, startPage + maxPages - 1);

    if (endPage - startPage < maxPages - 1) {
      startPage = Math.max(0, endPage - maxPages + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    return pages;
  }

  onPageChange(page: number): void {
    if (page >= 0 && page < this.totalPages && page !== this.currentPage) {
      this.pageChange.emit(page);
    }
  }

  goToFirst(): void {
    this.onPageChange(0);
  }

  goToLast(): void {
    this.onPageChange(this.totalPages - 1);
  }

  goToPrevious(): void {
    this.onPageChange(this.currentPage - 1);
  }

  goToNext(): void {
    this.onPageChange(this.currentPage + 1);
  }
}