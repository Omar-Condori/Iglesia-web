import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DepartmentService } from '../../../core/services/department.service';
import { Department, DepartmentSection, DepartmentContent } from '../../../core/models/department.model';

@Component({
  selector: 'app-department-detail',
  templateUrl: './department-detail.component.html',
  styleUrls: ['./department-detail.component.scss']
})
export class DepartmentDetailComponent implements OnInit {
  department: Department | null = null;
  sections: DepartmentSection[] = [];
  selectedSection: DepartmentSection | null = null;
  content: DepartmentContent[] = [];
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private departmentService: DepartmentService
  ) { }

  ngOnInit(): void {
    const slug = this.route.snapshot.paramMap.get('slug');
    if (slug) {
      this.loadDepartment(slug);
    }
  }

  loadDepartment(slug: string): void {
    this.loading = true;
    this.departmentService.getDepartmentBySlug(slug).subscribe({
      next: (response) => {
        this.department = response.data;
        this.sections = response.data.sections || [];

        // Select first section by default
        if (this.sections.length > 0) {
          this.selectSection(this.sections[0]);
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading department:', error);
        this.error = true;
        this.loading = false;
      }
    });
  }

  selectSection(section: DepartmentSection): void {
    this.selectedSection = section;
    this.loadSectionContent(section.id);
  }

  loadSectionContent(sectionId: number): void {
    this.departmentService.getContentBySection(sectionId).subscribe({
      next: (response) => {
        this.content = response.data;
      },
      error: (error) => console.error('Error loading content:', error)
    });
  }
}
