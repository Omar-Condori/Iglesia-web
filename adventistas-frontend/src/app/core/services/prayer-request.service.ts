import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CreatePrayerRequest, PrayerRequest, RequestStatus } from '../models/prayer-request.model';

@Injectable({
    providedIn: 'root'
})
export class PrayerRequestService {
    private apiUrl = `${environment.apiUrl}/prayer-requests`;

    constructor(private http: HttpClient) { }

    create(request: CreatePrayerRequest): Observable<any> {
        return this.http.post<any>(this.apiUrl, request);
    }

    getAll(page: number = 0, size: number = 10): Observable<any> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString());
        return this.http.get<any>(this.apiUrl, { params });
    }

    getByStatus(status: RequestStatus, page: number = 0, size: number = 10): Observable<any> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString());
        return this.http.get<any>(`${this.apiUrl}/status/${status}`, { params });
    }

    getById(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    getMyRequests(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/my-requests`);
    }

    updateStatus(id: number, status: RequestStatus): Observable<any> {
        const params = new HttpParams().set('status', status);
        return this.http.patch<any>(`${this.apiUrl}/${id}/status`, null, { params });
    }

    delete(id: number): Observable<any> {
        return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }

    countByStatus(status: RequestStatus): Observable<any> {
        const params = new HttpParams().set('status', status);
        return this.http.get<any>(`${this.apiUrl}/stats/count-by-status`, { params });
    }
}
