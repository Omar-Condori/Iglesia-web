import { Component, OnInit } from '@angular/core';
import { LoaderService } from '../../../core/services/loader.service';

/**
 * Componente de loader global
 */
@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {
  loading = false;

  constructor(private loaderService: LoaderService) {}

  ngOnInit(): void {
    this.loaderService.loading$.subscribe(
      isLoading => this.loading = isLoading
    );
  }
}