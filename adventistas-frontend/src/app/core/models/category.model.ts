// Modelo de categoría
export interface Category {
  id: number;
  name: string;
  slug: string;
  description?: string;
  icon?: string;
  color?: string;
  isActive: boolean;
  sortOrder: number;
  createdAt: string;
}

export interface CreateCategoryRequest {
  name: string;
  description?: string;
  color?: string;
  sortOrder?: number;
}