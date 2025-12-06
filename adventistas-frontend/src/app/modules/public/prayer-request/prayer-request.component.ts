import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PrayerRequestService } from '../../../core/services/prayer-request.service';
import { PreferenceType, Urgency, PREFERENCE_TYPE_LABELS, URGENCY_LABELS } from '../../../core/models/prayer-request.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prayer-request',
  template: `
<div class="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
  <div class="max-w-2xl mx-auto">
    <div class="text-center mb-8">
      <i class="fas fa-praying-hands text-6xl text-blue-600 mb-4"></i>
      <h1 class="text-4xl font-bold text-gray-900 mb-2">Pedir Oración</h1>
      <p class="text-lg text-gray-600">Comparte tu petición y  permitenos orar por ti</p>
    </div>

    <div class="bg-white rounded-lg shadow-lg p-8">
      <form [formGroup]="requestForm" (ngSubmit)="onSubmit()">
        <!-- Teléfono -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">
            <i class="fas fa-phone mr-2"></i>Número de Celular *
          </label>
          <input type="tel" formControlName="phone" 
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600" 
            placeholder="Ej: 987654321" />
          <p *ngIf="hasError('phone', 'required')" class="text-red-600 text-sm mt-1">El número es requerido</p>
        </div>

        <!-- Distrito -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">
            <i class="fas fa-map-marker-alt mr-2"></i>Distrito / Comunidad *
          </label>
          <input type="text" formControlName="district"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600"
            placeholder="Ej: Centro" />
          <p *ngIf="hasError('district', 'required')" class="text-red-600 text-sm mt-1">El distrito es requerido</p>
        </div>

        <!-- Preferencia -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">
            <i class="fas fa-list mr-2"></i>Preferencia *
          </label>
          <div class="space-y-3">
            <label *ngFor="let type of preferenceTypes" 
              class="flex items-center p-4 border rounded-lg cursor-pointer hover:bg-blue-50">
              <input type="radio" formControlName="preferenceType" [value]="type" class="w-5 h-5" />
              <span class="ml-3">{{ preferenceTypeLabels[type] }}</span>
            </label>
          </div>
        </div>

        <!-- Urgencia -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">
            <i class="fas fa-exclamation-circle mr-2"></i>Urgencia *
          </label>
          <select formControlName="urgency" class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-blue-600">
            <option *ngFor="let urgency of urgencies" [value]="urgency">{{ urgencyLabels[urgency] }}</option>
          </select>
        </div>

        <!-- Mensaje -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">
            <i class="fas fa-comment mr-2"></i>Mensaje (Opcional)
          </label>
          <textarea formControlName="message" rows="4"
            class="w-full px-4 py-3 border rounded-lg focus:ring-2 focus:ring-blue-600"
            placeholder="Comparte más detalles..."></textarea>
        </div>

        <!-- Notificaciones -->
        <div class="mb-8">
          <label class="flex items-center cursor-pointer">
            <input type="checkbox" formControlName="wantsNotifications" class="w-5 h-5 rounded" />
            <span class="ml-3"><i class="fas fa-bell mr-2"></i>Deseo recibir notificaciones por email</span>
          </label>
        </div>

        <!-- Botones -->
        <div class="flex gap-4">
          <button type="button" (click)="cancel()" 
            class="flex-1 px-6 py-3 border rounded-lg hover:bg-gray-50">Cancelar</button>
          <button type="submit" [disabled]="isSubmitting"
            class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
            <i *ngIf="!isSubmitting" class="fas fa-paper-plane mr-2"></i>
            <i *ngIf="isSubmitting" class="fas fa-spinner fa-spin mr-2"></i>
            {{ isSubmitting ? 'Enviando...' : 'Enviar Petición' }}
          </button>
        </div>
      </form>
    </div>

    <div class="mt-6 bg-blue-50 border border-blue-200 rounded-lg p-4">
      <p class="text-sm text-blue-800">
        <i class="fas fa-info-circle mr-2"></i>
        Tu petición será recibida por nuestro equipo pastoral
      </p>
    </div>
  </div>
</div>
  `,
  styleUrls: ['./prayer-request.component.scss']
})
export class PrayerRequestComponent implements OnInit {
  requestForm!: FormGroup;
  isSubmitting = false;

  preferenceTypes = Object.values(PreferenceType);
  urgencies = Object.values(Urgency);

  preferenceTypeLabels = PREFERENCE_TYPE_LABELS;
  urgencyLabels = URGENCY_LABELS;

  constructor(
    private fb: FormBuilder,
    private prayerRequestService: PrayerRequestService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.requestForm = this.fb.group({
      phone: ['', [Validators.required, Validators.pattern(/^\d{9,15}$/)]],
      district: ['', [Validators.required, Validators.minLength(3)]],
      preferenceType: [PreferenceType.PRAYER_ONLY, Validators.required],
      urgency: [Urgency.MEDIUM, Validators.required],
      message: [''],
      wantsNotifications: [false]
    });
  }

  onSubmit(): void {
    if (this.requestForm.invalid) {
      Object.keys(this.requestForm.controls).forEach(key => {
        this.requestForm.get(key)?.markAsTouched();
      });
      return;
    }

    this.isSubmitting = true;

    this.prayerRequestService.create(this.requestForm.value).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: '¡Petición Enviada!',
          text: 'Tu petición de oración ha sido recibida. Estaremos orando por ti.',
          confirmButtonColor: '#1e40af',
          timer: 3000
        }).then(() => {
          this.router.navigate(['/']);
        });
      },
      error: (error) => {
        this.isSubmitting = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.error?.message || 'No se pudo enviar la petición. Inténtalo de nuevo.',
          confirmButtonColor: '#1e40af'
        });
      }
    });
  }

  hasError(field: string, error: string): boolean {
    const control = this.requestForm.get(field);
    return !!(control && control.hasError(error) && control.touched);
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
