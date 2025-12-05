import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NewsService } from '../../../core/services/news.service';
import { CategoryService } from '../../../core/services/category.service';
import { News } from '../../../core/models/news.model';
import { Category } from '../../../core/models/category.model';

@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.scss']
})
export class NewsListComponent implements OnInit {
  newsList: News[] = [];
  categories: Category[] = [];
  selectedCategory: number | null = null;
  
  currentPage = 0;
  pageSize = 12;
  totalPages = 0;
  totalElements = 0;
  
  isLoading = false;

  constructor(
    private newsService: NewsService,
    private categoryService: CategoryService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    
    // Verificar si hay un filtro de categoría en la ruta
    this.route.queryParams.subscribe(params => {
      if (params['category']) {
        this.selectedCategory = +params['category'];
      }
      this.loadNews();
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

  loadNews(): void {
    this.isLoading = true;
    
    const service$ = this.selectedCategory 
      ? this.newsService.getByCategory(this.selectedCategory, this.currentPage, this.pageSize)
      : this.newsService.getPublished(this.currentPage, this.pageSize);

    service$.subscribe({
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

  onCategoryChange(categoryId: number | null): void {
    this.selectedCategory = categoryId;
    this.currentPage = 0;
    this.loadNews();
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadNews();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}