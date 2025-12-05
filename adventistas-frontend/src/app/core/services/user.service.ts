import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse, PageResponse } from '../models/base-response.model';
import { User, CreateUserRequest, UpdateUserRequest, UserResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) { }

  getAll(page: number = 0, size: number = 10): Observable<BaseResponse<PageResponse<UserResponse>>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<BaseResponse<PageResponse<UserResponse>>>(this.apiUrl, { params });
  }

  getById(id: number): Observable<BaseResponse<UserResponse>> {
    return this.http.get<BaseResponse<UserResponse>>(`${this.apiUrl}/${id}`);
  }

  create(request: CreateUserRequest): Observable<BaseResponse<UserResponse>> {
    return this.http.post<BaseResponse<UserResponse>>(this.apiUrl, request);
  }

  update(id: number, request: UpdateUserRequest): Observable<BaseResponse<UserResponse>> {
    return this.http.put<BaseResponse<UserResponse>>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<BaseResponse<void>> {
    return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${id}`);
  }
}