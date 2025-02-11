import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslationService } from './translation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [RouterOutlet, TranslateModule, CommonModule]
})
export class AppComponent implements OnInit {
  translationService = inject(TranslationService);
  translateService = inject(TranslateService);

  constructor() {}

  ngOnInit() {
    this.translationService.updateTranslations();  // Kezdeményezzük a fordítások frissítését az inicializáláskor
  }

  changeLanguage(lang: string) {
    this.translationService.changeLanguage(lang);  // A nyelv váltását a TranslationService kezeli
  }
}
