import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicRoutingModule } from './public-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NewsListComponent } from './news-list/news-list.component';
import { NewsDetailComponent } from './news-detail/news-detail.component';
import { ChurchesListComponent } from './churches-list/churches-list.component';
import { RegisterComponent } from './register/register.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';
import { AboutSectionComponent } from './about-section/about-section.component';
import { PrayerRequestComponent } from './prayer-request/prayer-request.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/**
 * Módulo público de la aplicación
 */
@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    NewsListComponent,
    NewsDetailComponent,
    ChurchesListComponent,
    RegisterComponent,
    DepartmentDetailComponent,
    AboutSectionComponent,
    PrayerRequestComponent
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class PublicModule { }