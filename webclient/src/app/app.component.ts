import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslationService } from './translation.service';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [TranslateModule]
})
export class AppComponent implements OnInit {
  title = 'webclient';

  constructor(public translationService: TranslationService, private translate: TranslateService) {}

  ngOnInit() {
    //TODO Sajnos e nélkül a hívás nélkül inicializáláskor a kulcsot jeleníti meg. Ez átmeneti megoldás...
    this.changeLanguage(this.translate.getDefaultLang());
    //this.translationService.updateTranslations();
  }

  changeLanguage(lang: string) {
    this.translationService.changeLanguage(lang);
    this.translationService.updateTranslations();
  }
}
