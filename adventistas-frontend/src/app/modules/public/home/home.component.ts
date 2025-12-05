import { Component, OnInit } from '@angular/core';
import { NewsService } from '../../../core/services/news.service';
import { CourseService } from '../../../core/services/course.service';
import { News } from '../../../core/models/news.model';
import { Course } from '../../../core/models/course.model';

/**
 * Componente de página de inicio
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  latestNews: News[] = [];
  featuredCourses: Course[] = [];
  isLoading = false;

  constructor(
    private newsService: NewsService,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.loadLatestNews();
    this.loadFeaturedCourses();
  }

  loadLatestNews(): void {
    this.newsService.getPublished(0, 6).subscribe({
      next: (response) => {
        this.latestNews = response.data.content;
      },
      error: (error) => {
        console.error('Error loading news:', error);
      }
    });
  }

  loadFeaturedCourses(): void {
    this.courseService.getFeatured(0, 4).subscribe({
      next: (response) => {
        this.featuredCourses = response.data.content;
      },
      error: (error) => {
        console.error('Error loading courses:', error);
      }
    });
  }
}