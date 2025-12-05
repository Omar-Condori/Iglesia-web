import { Pipe, PipeTransform } from '@angular/core';

/**
 * Pipe para mostrar tiempo relativo (hace 2 horas, hace 3 días, etc)
 */
@Pipe({
  name: 'timeAgo'
})
export class TimeAgoPipe implements PipeTransform {
  transform(value: string | Date): string {
    if (!value) return '';

    const date = typeof value === 'string' ? new Date(value) : value;
    const seconds = Math.floor((new Date().getTime() - date.getTime()) / 1000);

    if (seconds < 60) return 'Hace un momento';
    
    const intervals: { [key: string]: number } = {
      'año': 31536000,
      'mes': 2592000,
      'semana': 604800,
      'día': 86400,
      'hora': 3600,
      'minuto': 60
    };

    for (const [name, secondsInInterval] of Object.entries(intervals)) {
      const interval = Math.floor(seconds / secondsInInterval);
      if (interval >= 1) {
        return `Hace ${interval} ${name}${interval > 1 ? (name === 'mes' ? 'es' : 's') : ''}`;
      }
    }

    return 'Hace un momento';
  }
}