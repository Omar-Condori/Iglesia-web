import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse } from '../models/base-response.model';
import { Department, CreateDepartmentRequest, DepartmentContent } from '../models/department.model';

/**
 * Servicio para gestionar departamentos
 */
@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private apiUrl = `${environment.apiUrl}/departments`;

  constructor(private http: HttpClient) { }

  getAllDepartments(): Observable<BaseResponse<Department[]>> {
    return this.http.get<BaseResponse<Department[]>>(this.apiUrl);
  }

  getActiveDepartments(): Observable<BaseResponse<Department[]>> {
    return this.http.get<BaseResponse<Department[]>>(`${this.apiUrl}/active`);
  }

  getById(id: number): Observable<BaseResponse<Department>> {
    return this.http.get<BaseResponse<Department>>(`${this.apiUrl}/${id}`);
  }

  getDepartmentBySlug(slug: string): Observable<BaseResponse<Department>> {
    return this.http.get<BaseResponse<Department>>(`${this.apiUrl}/slug/${slug}`);
  }

  getContentBySection(sectionId: number): Observable<BaseResponse<DepartmentContent[]>> {
    return this.http.get<BaseResponse<DepartmentContent[]>>(`${this.apiUrl}/sections/${sectionId}/content`);
  }

  getContentById(id: number): Observable<BaseResponse<DepartmentContent>> {
    return this.http.get<BaseResponse<DepartmentContent>>(`${this.apiUrl}/content/${id}`);
  }

  create(request: CreateDepartmentRequest): Observable<BaseResponse<Department>> {
    return this.http.post<BaseResponse<Department>>(this.apiUrl, request);
  }

  update(id: number, request: CreateDepartmentRequest): Observable<BaseResponse<Department>> {
    return this.http.put<BaseResponse<Department>>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }

  toggleActive(id: number): Observable<BaseResponse<void>> {
    return this.http.patch<BaseResponse<void>>(`${this.apiUrl}/${id}/toggle`, {});
  }
}