// Modelo de video
export interface Video {
  id: number;
  title: string;
  slug: string;
  description?: string;
  videoUrl: string;
  thumbnailUrl?: string;
  platform: string;
  durationSeconds?: number;
  viewsCount: number;
  isActive: boolean;
  categoryName: string;
  departmentName?: string;
  uploaderName: string;
  createdAt: string;
}

export interface CreateVideoRequest {
  title: string;
  description?: string;
  videoUrl: string;
  thumbnailUrl?: string;
  platform: string;
  durationSeconds?: number;
  categoryId: number;
  departmentId?: number;
}