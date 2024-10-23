import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslateService, TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ 'WELCOME_MESSAGE' | translate }}</h1>
    <button (click)="changeLanguage('en')">English</button>
    <button (click)="changeLanguage('hu')">Magyar</button>
  `,
  standalone: true,
  imports: [RouterOutlet, TranslateModule]
})
export class AppComponent {
  title = 'webclient';

  constructor(private translate: TranslateService) {
    translate.setDefaultLang('hu');
  }

  changeLanguage(lang: string) {
    this.translate.use(lang);
  }
}
