// Modelos de noticias
export interface News {
  id: number;
  title: string;
  slug: string;
  summary?: string;
  content: string;
  featuredImage?: string;
  status: NewsStatus;
  isFeatured: boolean;
  allowComments: boolean;
  viewsCount: number;
  publishedAt?: string;
  authorName: string;
  categoryName?: string;
  departmentName?: string;
  createdAt: string;
  updatedAt?: string;
  imageUrl?: string;
  categoryId?: number;
  tags?: string[];
  metaTitle?: string;
  metaDescription?: string;
  metaKeywords?: string;
}

export enum NewsStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  ARCHIVED = 'ARCHIVED'
}

export interface CreateNewsRequest {
  title: string;
  summary?: string;
  content: string;
  featuredImage?: string;
  categoryId: number;
  departmentId?: number;
  isFeatured?: boolean;
  allowComments?: boolean;
}

export interface UpdateNewsRequest {
  title?: string;
  summary?: string;
  content?: string;
  featuredImage?: string;
  categoryId?: number;
  departmentId?: number;
  isFeatured?: boolean;
  allowComments?: boolean;
}