import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Adventistas.org';

  constructor(
    private translate: TranslateService,
    private router: Router
  ) {
    // Configurar idiomas disponibles
    this.translate.addLangs(['es', 'pt']);
    this.translate.setDefaultLang('es');

    // Detectar idioma del navegador
    const browserLang = this.translate.getBrowserLang();
    const lang = browserLang && browserLang.match(/es|pt/) ? browserLang : 'es';
    this.translate.use(lang);
  }

  ngOnInit(): void {
    // Scroll to top on route change
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      window.scrollTo(0, 0);
    });
  }
}