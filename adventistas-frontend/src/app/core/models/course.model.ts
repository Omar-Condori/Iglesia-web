// Modelo de curso
export interface Course {
  id: number;
  title: string;
  slug: string;
  description?: string;
  thumbnailUrl?: string;
  imageUrl?: string;
  level: string;
  durationMinutes?: number;
  isActive: boolean;
  isFeatured: boolean;
  enrollmentsCount: number;
  lessonsCount?: number;
  categoryName: string;
  instructorName: string;
  createdAt: string;
}

export interface CreateCourseRequest {
  title: string;
  description?: string;
  thumbnailUrl?: string;
  level: string;
  durationMinutes?: number;
  categoryId: number;
  instructorId: number;
}

export interface Lesson {
  id: number;
  title: string;
  slug: string;
  description?: string;
  content?: string;
  videoUrl?: string;
  durationMinutes?: number;
  sortOrder: number;
  isActive: boolean;
  isFree: boolean;
  courseTitle: string;
  createdAt: string;
}

export interface CreateLessonRequest {
  title: string;
  description?: string;
  content?: string;
  videoUrl?: string;
  durationMinutes?: number;
  sortOrder?: number;
  isFree?: boolean;
  courseId: number;
}