import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse, PageResponse } from '../models/base-response.model';
import { News, CreateNewsRequest, UpdateNewsRequest } from '../models/news.model';

/**
 * Servicio para gestionar noticias
 */
@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private apiUrl = `${environment.apiUrl}/news`;

  constructor(private http: HttpClient) { }

  /**
   * Obtiene todas las noticias con paginación
   */
  getAll(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<News>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<News>>>(this.apiUrl, { params });
  }

  /**
   * Obtiene noticias publicadas (público)
   */
  getPublished(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<News>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<News>>>(`${this.apiUrl}/published`, { params });
  }

  /**
   * Obtiene una noticia por ID
   */
  getById(id: number): Observable<BaseResponse<News>> {
    return this.http.get<BaseResponse<News>>(`${this.apiUrl}/${id}`);
  }

  /**
   * Obtiene una noticia por slug
   */
  getBySlug(slug: string): Observable<BaseResponse<News>> {
    return this.http.get<BaseResponse<News>>(`${this.apiUrl}/slug/${slug}`);
  }

  /**
   * Crea una nueva noticia
   */
  create(request: CreateNewsRequest): Observable<BaseResponse<News>> {
    return this.http.post<BaseResponse<News>>(this.apiUrl, request);
  }

  /**
   * Actualiza una noticia existente
   */
  update(id: number, request: UpdateNewsRequest): Observable<BaseResponse<News>> {
    return this.http.put<BaseResponse<News>>(`${this.apiUrl}/${id}`, request);
  }

  /**
   * Publica una noticia
   */
  publish(id: number): Observable<BaseResponse<void>> {
    return this.http.post<BaseResponse<void>>(`${this.apiUrl}/${id}/publish`, {});
  }

  /**
   * Elimina una noticia
   */
  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }

  /**
   * Busca noticias por categoría
   */
  getByCategory(categoryId: number, page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<News>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<News>>>(`${this.apiUrl}/category/${categoryId}`, { params });
  }
}