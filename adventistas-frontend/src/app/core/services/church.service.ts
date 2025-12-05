import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse, PageResponse } from '../models/base-response.model';
import { Church, CreateChurchRequest } from '../models/church.model';

@Injectable({
  providedIn: 'root'
})
export class ChurchService {
  private apiUrl = `${environment.apiUrl}/churches`;

  constructor(private http: HttpClient) { }

  getAll(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<Church>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<Church>>>(this.apiUrl, { params });
  }

  getActive(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<Church>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<Church>>>(`${this.apiUrl}`, { params });
  }

  getById(id: number): Observable<BaseResponse<Church>> {
    return this.http.get<BaseResponse<Church>>(`${this.apiUrl}/${id}`);
  }

  getBySlug(slug: string): Observable<BaseResponse<Church>> {
    return this.http.get<BaseResponse<Church>>(`${this.apiUrl}/slug/${slug}`);
  }

  getByUnion(unionId: number): Observable<BaseResponse<Church[]>> {
    return this.http.get<BaseResponse<Church[]>>(`${this.apiUrl}/union/${unionId}`);
  }

  getByCity(city: string): Observable<BaseResponse<Church[]>> {
    return this.http.get<BaseResponse<Church[]>>(`${this.apiUrl}/city/${city}`);
  }

  create(request: CreateChurchRequest): Observable<BaseResponse<Church>> {
    return this.http.post<BaseResponse<Church>>(this.apiUrl, request);
  }

  update(id: number, request: CreateChurchRequest): Observable<BaseResponse<Church>> {
    return this.http.put<BaseResponse<Church>>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }
}