import { Component, OnInit } from '@angular/core';
import { NewsService } from '../../../core/services/news.service';
import { ChurchService } from '../../../core/services/church.service';
import { CourseService } from '../../../core/services/course.service';
import { UserService } from '../../../core/services/user.service';

interface DashboardStats {
  totalNews: number;
  totalChurches: number;
  totalCourses: number;
  totalUsers: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats = {
    totalNews: 0,
    totalChurches: 0,
    totalCourses: 0,
    totalUsers: 0
  };

  today: Date = new Date();
  recentNews: any[] = [];
  isLoading = false;

  constructor(
    private newsService: NewsService,
    private churchService: ChurchService,
    private courseService: CourseService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadStats();
    this.loadRecentNews();
  }

  loadStats(): void {
    // Cargar estadísticas
    this.newsService.getAll(0, 1).subscribe(response => {
      this.stats.totalNews = response.data.totalElements;
    });

    this.churchService.getAll(0, 1).subscribe(response => {
      this.stats.totalChurches = response.data.totalElements;
    });

    this.courseService.getAll(0, 1).subscribe(response => {
      this.stats.totalCourses = response.data.totalElements;
    });

    this.userService.getAll(0, 1).subscribe(response => {
      this.stats.totalUsers = response.data.totalElements;
    });
  }

  loadRecentNews(): void {
    this.newsService.getAll(0, 5).subscribe({
      next: (response) => {
        this.recentNews = response.data.content;
      },
      error: (error) => {
        console.error('Error loading recent news:', error);
      }
    });
  }
}