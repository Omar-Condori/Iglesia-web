import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NewsService } from '../../../core/services/news.service';
import { CourseService } from '../../../core/services/course.service';
import { TokenService } from '../../../core/services/token.service';
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
    private courseService: CourseService,
    private tokenService: TokenService,
    private router: Router
  ) { }


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


  onPrayerRequest(): void {
    console.log('🙏 Click en Pedir Oración');
    const hasToken = this.tokenService.hasToken();
    console.log('Token encontrado:', hasToken);

    if (hasToken && !this.tokenService.isTokenExpired()) {
      console.log('Navegando a /prayer-request');
      this.router.navigate(['/prayer-request']);
    } else {
      console.log('No autenticado, guardando returnUrl y redirigiendo a login');
      localStorage.setItem('returnUrl', '/prayer-request');
      this.router.navigate(['/login']);
    }
  }

  onHomeVisitRequest(): void {
    console.log('🏠 Click en Visitar Mi Hogar');
    const hasToken = this.tokenService.hasToken();
    console.log('Token encontrado:', hasToken);

    if (hasToken && !this.tokenService.isTokenExpired()) {
      console.log('Navegando a /home-visit-request');
      this.router.navigate(['/home-visit-request']);
    } else {
      console.log('No autenticado, guardando returnUrl y redirigiendo a login');
      localStorage.setItem('returnUrl', '/home-visit-request');
      this.router.navigate(['/login']);
    }
  }
}