// Modelo de descarga
export interface Download {
  id: number;
  title: string;
  slug: string;
  description?: string;
  fileUrl: string;
  fileType: string;
  fileSize: number;
  downloadsCount: number;
  isActive: boolean;
  categoryName: string;
  departmentName?: string;
  uploaderName: string;
  createdAt: string;
}

export interface CreateDownloadRequest {
  title: string;
  description?: string;
  fileUrl: string;
  fileType: string;
  fileSize: number;
  categoryId: number;
  departmentId?: number;
}