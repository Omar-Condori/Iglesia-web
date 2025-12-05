import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse, PageResponse } from '../models/base-response.model';
import { Course, CreateCourseRequest, Lesson, CreateLessonRequest } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = `${environment.apiUrl}/courses`;
  private lessonsUrl = `${environment.apiUrl}/lessons`;

  constructor(private http: HttpClient) { }

  // Cursos
  getAll(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<Course>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<Course>>>(this.apiUrl, { params });
  }

  getFeatured(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<Course>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<Course>>>(`${this.apiUrl}/featured`, { params });
  }

  getById(id: number): Observable<BaseResponse<Course>> {
    return this.http.get<BaseResponse<Course>>(`${this.apiUrl}/${id}`);
  }

  getBySlug(slug: string): Observable<BaseResponse<Course>> {
    return this.http.get<BaseResponse<Course>>(`${this.apiUrl}/slug/${slug}`);
  }

  create(request: CreateCourseRequest): Observable<BaseResponse<Course>> {
    return this.http.post<BaseResponse<Course>>(this.apiUrl, request);
  }

  update(id: number, request: CreateCourseRequest): Observable<BaseResponse<Course>> {
    return this.http.put<BaseResponse<Course>>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }

  // Lecciones
  getLessonsByCourse(courseId: number): Observable<BaseResponse<Lesson[]>> {
    return this.http.get<BaseResponse<Lesson[]>>(`${this.lessonsUrl}/course/${courseId}`);
  }

  getFreeLessonsByCourse(courseId: number): Observable<BaseResponse<Lesson[]>> {
    return this.http.get<BaseResponse<Lesson[]>>(`${this.lessonsUrl}/course/${courseId}/free`);
  }

  getLessonById(id: number): Observable<BaseResponse<Lesson>> {
    return this.http.get<BaseResponse<Lesson>>(`${this.lessonsUrl}/${id}`);
  }

  createLesson(request: CreateLessonRequest): Observable<BaseResponse<Lesson>> {
    return this.http.post<BaseResponse<Lesson>>(this.lessonsUrl, request);
  }

  updateLesson(id: number, request: CreateLessonRequest): Observable<BaseResponse<Lesson>> {
    return this.http.put<BaseResponse<Lesson>>(`${this.lessonsUrl}/${id}`, request);
  }

  deleteLesson(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.lessonsUrl}/${id}`);
  }
}