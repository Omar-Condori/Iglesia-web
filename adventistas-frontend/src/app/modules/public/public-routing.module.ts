import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicLayoutComponent } from '../../layouts/public-layout/public-layout.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NewsListComponent } from './news-list/news-list.component';
import { NewsDetailComponent } from './news-detail/news-detail.component';
import { ChurchesListComponent } from './churches-list/churches-list.component';
import { RegisterComponent } from './register/register.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';
import { AboutSectionComponent } from './about-section/about-section.component';

const routes: Routes = [
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'news', component: NewsListComponent },
      { path: 'news/:slug', component: NewsDetailComponent },
      { path: 'churches', component: ChurchesListComponent },
      { path: 'departments/:slug', component: DepartmentDetailComponent },
      { path: 'about/:slug', component: AboutSectionComponent },
      // { path: 'courses', loadChildren: () => import('./courses/courses.module').then(m => m.CoursesModule) },
      // { path: 'downloads', loadChildren: () => import('./downloads/downloads.module').then(m => m.DownloadsModule) },
      // { path: 'videos', loadChildren: () => import('./videos/videos.module').then(m => m.VideosModule) }
    ]
  },
  { path: 'login', component: LoginComponent },
  {
    path: 'register',
    component: RegisterComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }