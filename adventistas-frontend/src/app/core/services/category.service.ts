import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse } from '../models/base-response.model';
import { Category, CreateCategoryRequest } from '../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = `${environment.apiUrl}/categories`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<BaseResponse<Category[]>> {
    return this.http.get<BaseResponse<Category[]>>(this.apiUrl);
  }

  getActive(): Observable<BaseResponse<Category[]>> {
    return this.http.get<BaseResponse<Category[]>>(`${this.apiUrl}/active`);
  }

  getById(id: number): Observable<BaseResponse<Category>> {
    return this.http.get<BaseResponse<Category>>(`${this.apiUrl}/${id}`);
  }

  create(request: CreateCategoryRequest): Observable<BaseResponse<Category>> {
    return this.http.post<BaseResponse<Category>>(this.apiUrl, request);
  }

  update(id: number, request: CreateCategoryRequest): Observable<BaseResponse<Category>> {
    return this.http.put<BaseResponse<Category>>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }

  toggleActive(id: number): Observable<BaseResponse<void>> {
    return this.http.patch<BaseResponse<void>>(`${this.apiUrl}/${id}/toggle`, {});
  }
}