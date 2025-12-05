import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsService } from '../../../core/services/news.service';
import { News } from '../../../core/models/news.model';
import { Meta, Title } from '@angular/platform-browser';

@Component({
  selector: 'app-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.scss']
})
export class NewsDetailComponent implements OnInit {
  news: News | null = null;
  relatedNews: News[] = [];
  isLoading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private newsService: NewsService,
    private titleService: Title,
    private metaService: Meta
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const slug = params['slug'];
      if (slug) {
        this.loadNews(slug);
      }
    });
  }

  loadNews(slug: string): void {
    this.isLoading = true;
    this.newsService.getBySlug(slug).subscribe({
      next: (response) => {
        this.news = response.data;
        this.isLoading = false;

        // SEO Meta tags
        this.titleService.setTitle(`${this.news.title} - Adventistas.org`);
        this.metaService.updateTag({ name: 'description', content: this.news.summary || '' });
        this.metaService.updateTag({ property: 'og:title', content: this.news.title });
        this.metaService.updateTag({ property: 'og:description', content: this.news.summary || '' });
        this.metaService.updateTag({ property: 'og:image', content: this.news.imageUrl || '' });

        // Cargar noticias relacionadas
        if (this.news.categoryId) {
          this.loadRelatedNews(this.news.categoryId, this.news.id);
        }
      },
      error: (error) => {
        console.error('Error loading news:', error);
        this.isLoading = false;
        this.router.navigate(['/news']);
      }
    });
  }

  loadRelatedNews(categoryId: number, excludeId: number): void {
    this.newsService.getByCategory(categoryId, 0, 4).subscribe({
      next: (response) => {
        this.relatedNews = response.data.content.filter(n => n.id !== excludeId);
      },
      error: (error) => {
        console.error('Error loading related news:', error);
      }
    });
  }

  shareOnFacebook(): void {
    const url = encodeURIComponent(window.location.href);
    window.open(`https://www.facebook.com/sharer/sharer.php?u=${url}`, '_blank');
  }

  shareOnTwitter(): void {
    const url = encodeURIComponent(window.location.href);
    const text = encodeURIComponent(this.news?.title || '');
    window.open(`https://twitter.com/intent/tweet?url=${url}&text=${text}`, '_blank');
  }

  shareOnWhatsApp(): void {
    const url = encodeURIComponent(window.location.href);
    const text = encodeURIComponent(this.news?.title || '');
    window.open(`https://wa.me/?text=${text}%20${url}`, '_blank');
  }
}