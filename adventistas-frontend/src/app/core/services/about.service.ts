import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { BaseResponse } from '../models/base-response.model';
import { AboutSection } from '../models/about.model';

@Injectable({
    providedIn: 'root'
})
export class AboutService {
    private apiUrl = `${environment.apiUrl}/about`;

    constructor(private http: HttpClient) { }

    getActiveSections(): Observable<BaseResponse<AboutSection[]>> {
        return this.http.get<BaseResponse<AboutSection[]>>(this.apiUrl);
    }

    getSectionBySlug(slug: string): Observable<BaseResponse<AboutSection>> {
        return this.http.get<BaseResponse<AboutSection>>(`${this.apiUrl}/${slug}`);
    }

    updateSection(id: number, request: any): Observable<BaseResponse<AboutSection>> {
        return this.http.put<BaseResponse<AboutSection>>(`${this.apiUrl}/${id}`, request);
    }
}
