import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AboutService } from '../../../core/services/about.service';
import { AboutSection } from '../../../core/models/about.model';

@Component({
  selector: 'app-about-section',
  templateUrl: './about-section.component.html',
  styleUrls: ['./about-section.component.scss']
})
export class AboutSectionComponent implements OnInit {
  section: AboutSection | null = null;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private aboutService: AboutService
  ) { }

  ngOnInit(): void {
    const slug = this.route.snapshot.paramMap.get('slug');
    if (slug) {
      this.loadSection(slug);
    }
  }

  loadSection(slug: string): void {
    this.loading = true;
    this.aboutService.getSectionBySlug(slug).subscribe({
      next: (response) => {
        this.section = response.data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading section:', error);
        this.error = true;
        this.loading = false;
      }
    });
  }
}
