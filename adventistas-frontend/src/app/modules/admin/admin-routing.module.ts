import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLayoutComponent } from '../../layouts/admin-layout/admin-layout.component';
import { AuthGuard } from '../../core/guards/auth.guard';
import { RoleGuard } from '../../core/guards/role.guard';

import { DashboardComponent } from './dashboard/dashboard.component';
import { NewsListComponent } from './news/news-list/news-list.component';
import { NewsFormComponent } from './news/news-form/news-form.component';
import { ChurchesListComponent } from './churches/churches-list/churches-list.component';
import { ChurchFormComponent } from './churches/church-form/church-form.component';
import { CategoriesComponent } from './categories/categories.component';
import { DepartmentsComponent } from './departments/departments.component';
import { UsersListComponent } from './users/users-list/users-list.component';
import { UserFormComponent } from './users/user-form/user-form.component';


const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { permissions: ['news.view', 'news.create', 'news.edit', 'news.publish', 'users.view', 'users.create'] },
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },

      { path: 'news', component: NewsListComponent },
      { path: 'news/new', component: NewsFormComponent },
      { path: 'news/edit/:id', component: NewsFormComponent },

      { path: 'churches', component: ChurchesListComponent },
      { path: 'churches/new', component: ChurchFormComponent },
      { path: 'churches/edit/:id', component: ChurchFormComponent },

      { path: 'categories', component: CategoriesComponent },
      { path: 'departments', component: DepartmentsComponent },

      { path: 'users', component: UsersListComponent, canActivate: [RoleGuard], data: { permissions: ['users.view'] } },
      { path: 'users/new', component: UserFormComponent, canActivate: [RoleGuard], data: { permissions: ['users.create'] } },
      { path: 'users/edit/:id', component: UserFormComponent, canActivate: [RoleGuard], data: { permissions: ['users.edit'] } },

      // { path: 'courses', loadChildren: () => import('./courses/courses-admin.module').then(m => m.CoursesAdminModule) },
      // { path: 'downloads', loadChildren: () => import('./downloads/downloads-admin.module').then(m => m.DownloadsAdminModule) },
      // { path: 'videos', loadChildren: () => import('./videos/videos-admin.module').then(m => m.VideosAdminModule) },
      // { path: 'unions', loadChildren: () => import('./unions/unions-admin.module').then(m => m.UnionsAdminModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }