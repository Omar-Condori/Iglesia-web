import { Component, Output, EventEmitter, Input } from '@angular/core';
import { MediaService } from '../../../core/services/media.service';

/**
 * Componente reutilizable para subir archivos
 */
@Component({
  selector: 'app-file-uploader',
  templateUrl: './file-uploader.component.html',
  styleUrls: ['./file-uploader.component.scss']
})
export class FileUploaderComponent {
  @Input() accept = 'image/*';
  @Input() type: 'image' | 'document' | 'video' = 'image';
  @Input() label = 'Seleccionar archivo';
  @Output() fileUploaded = new EventEmitter<string>();
  
  selectedFile: File | null = null;
  previewUrl: string | null = null;
  isUploading = false;
  uploadProgress = 0;

  constructor(private mediaService: MediaService) {}

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      
      // Mostrar preview para imágenes
      if (file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.previewUrl = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    }
  }

  upload(): void {
    if (!this.selectedFile) return;

    this.isUploading = true;
    this.mediaService.uploadFile(this.selectedFile, this.type).subscribe({
      next: (response) => {
        this.isUploading = false;
        this.fileUploaded.emit(response.data.url);
      },
      error: (error) => {
        this.isUploading = false;
        console.error('Error uploading file:', error);
      }
    });
  }

  clear(): void {
    this.selectedFile = null;
    this.previewUrl = null;
  }
}