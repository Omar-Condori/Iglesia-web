// Modelo de departamento
export interface Department {
  id: number;
  name: string;
  slug: string;
  description?: string;
  color?: string;
  icon?: string;
  isActive: boolean;
  sortOrder: number;
  createdAt?: string;
  sections?: DepartmentSection[];
}

export interface DepartmentSection {
  id: number;
  departmentId: number;
  name: string;
  slug: string;
  description?: string;
  icon?: string;
  sortOrder: number;
  isActive: boolean;
}

export interface DepartmentContent {
  id: number;
  sectionId: number;
  sectionName: string;
  departmentName: string;
  title: string;
  slug: string;
  content: string;
  excerpt?: string;
  featuredImage?: string;
  authorEmail?: string;
  authorId?: number;
  isPublished: boolean;
  publishedAt?: string;
  sortOrder: number;
  views: number;
  createdAt: string;
  updatedAt: string;
}

export interface CreateDepartmentRequest {
  name: string;
  description?: string;
  color?: string;
  icon?: string;
  sortOrder?: number;
}