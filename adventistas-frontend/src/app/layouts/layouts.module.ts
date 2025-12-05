import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';

import { PublicLayoutComponent } from './public-layout/public-layout.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';

@NgModule({
  declarations: [
    PublicLayoutComponent,
    AdminLayoutComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    PublicLayoutComponent,
    AdminLayoutComponent
  ]
})
export class LayoutsModule { }