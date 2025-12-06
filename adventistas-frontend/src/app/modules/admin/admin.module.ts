import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { AdminRoutingModule } from './admin-routing.module';

// Components
import { DashboardComponent } from './dashboard/dashboard.component';

// News
import { NewsListComponent } from './news/news-list/news-list.component';
import { NewsFormComponent } from './news/news-form/news-form.component';

// Churches
import { ChurchesListComponent } from './churches/churches-list/churches-list.component';
import { ChurchFormComponent } from './churches/church-form/church-form.component';

// Categories & Departments
import { CategoriesComponent } from './categories/categories.component';
import { DepartmentsComponent } from './departments/departments.component';

// Users
import { UsersListComponent } from './users/users-list/users-list.component';
import { UserFormComponent } from './users/user-form/user-form.component';

// Prayer Requests
import { PrayerRequestsComponent } from './prayer-requests/prayer-requests.component';

@NgModule({
  declarations: [
    DashboardComponent,
    NewsListComponent,
    NewsFormComponent,
    ChurchesListComponent,
    ChurchFormComponent,
    CategoriesComponent,
    DepartmentsComponent,
    UsersListComponent,
    UserFormComponent,
    PrayerRequestsComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }