import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

// Components
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoaderComponent } from './components/loader/loader.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { FileUploaderComponent } from './components/file-uploader/file-uploader.component';

// Pipes
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { TruncatePipe } from './pipes/truncate.pipe';
import { TimeAgoPipe } from './pipes/time-ago.pipe';

const COMPONENTS = [
  NavbarComponent,
  FooterComponent,
  LoaderComponent,
  PaginationComponent,
  FileUploaderComponent
];

const PIPES = [
  SafeHtmlPipe,
  TruncatePipe,
  TimeAgoPipe
];

@NgModule({
  declarations: [
    ...COMPONENTS,
    ...PIPES
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  exports: [
    ...COMPONENTS,
    ...PIPES,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ]
})
export class SharedModule { }