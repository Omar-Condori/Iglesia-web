import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

/**
 * Servicio para subir archivos multimedia
 */
@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private apiUrl = `${environment.mediaUrl}/upload`;

  constructor(private http: HttpClient) { }

  /**
   * Sube un archivo al servidor
   */
  uploadFile(file: File, type: 'image' | 'document' | 'video' = 'image'): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('type', type);

    return this.http.post(`${this.apiUrl}`, formData);
  }

  /**
   * Sube múltiples archivos
   */
  uploadMultipleFiles(files: File[], type: 'image' | 'document' | 'video' = 'image'): Observable<any> {
    const formData = new FormData();
    files.forEach(file => {
      formData.append('files', file);
    });
    formData.append('type', type);

    return this.http.post(`${this.apiUrl}/multiple`, formData);
  }

  /**
   * Obtiene la URL completa de un archivo
   */
  getFileUrl(path: string): string {
    if (path.startsWith('http')) {
      return path;
    }
    return `${environment.mediaUrl}/${path}`;
  }
}