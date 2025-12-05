import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse } from '../models/base-response.model';
import { UserDepartment, AssignDepartmentRequest } from '../models/user-department.model';

@Injectable({
    providedIn: 'root'
})
export class UserDepartmentService {
    private apiUrl = `${environment.apiUrl}/users`;

    constructor(private http: HttpClient) { }

    getUserDepartments(userId: number): Observable<BaseResponse<UserDepartment[]>> {
        return this.http.get<BaseResponse<UserDepartment[]>>(`${this.apiUrl}/${userId}/departments`);
    }

    assignDepartment(userId: number, request: AssignDepartmentRequest): Observable<BaseResponse<UserDepartment>> {
        return this.http.post<BaseResponse<UserDepartment>>(`${this.apiUrl}/${userId}/departments`, request);
    }

    removeDepartment(userId: number, departmentId: number): Observable<BaseResponse<void>> {
        return this.http.delete<BaseResponse<void>>(`${this.apiUrl}/${userId}/departments/${departmentId}`);
    }
}
