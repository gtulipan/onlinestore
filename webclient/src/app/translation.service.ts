import { Injectable } from '@angular/core';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { signal, Signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TranslationService {
  welcomeMessage = signal<string>('');
  userName = signal<string>('');

  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('en');
    console.log('Initializing translations...');
    this.updateTranslations();

    this.translate.onLangChange.subscribe(() => {
      this.updateTranslations();
    });
  }

  public updateTranslations() {
    console.log('Updating translations...');
    this.welcomeMessage.set(this.translate.instant('WELCOME_MESSAGE'));
    this.userName.set(this.translate.instant('USER_NAME'));
    console.log('New welcomeMessage:', this.welcomeMessage());
  }

  changeLanguage(lang: string) {
    console.log('Changing language to:', lang);
    this.translate.use(lang);
  }
}
