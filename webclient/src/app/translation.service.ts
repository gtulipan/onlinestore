import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { signal, Signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TranslationService {
  welcomeMessage = signal<string>('');
  userName = signal<string>('');
  loading = signal<boolean>(true);

  constructor(
    private translate: TranslateService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    if (isPlatformBrowser(this.platformId)) {
      const browserLang = (navigator.language || 'en').split('-')[0];
      this.loading.set(true);  // Betöltés előtt igazra állítjuk a `loading` állapotot
      this.translate.use(browserLang).subscribe(() => {
        this.updateTranslations().then(() => {
          this.loading.set(false);  // Betöltés után hamisra állítjuk a `loading` állapotot
        });
      });
    }
  }

  public async updateTranslations() {
    console.log('Updating translations...');
    this.welcomeMessage.set(await this.translate.get('WELCOME_MESSAGE').toPromise());
    this.userName.set(await this.translate.get('USER_NAME').toPromise());
    console.log('New welcomeMessage:', this.welcomeMessage());
  }

  changeLanguage(lang: string) {
    console.log('Changing language to:', lang);
    this.loading.set(true);  // Nyelvváltáskor igazra állítjuk a `loading` állapotot
    this.translate.use(lang).subscribe(() => {
      this.updateTranslations().then(() => {
        this.loading.set(false);  // Betöltés után hamisra állítjuk a `loading` állapotot
      });
    });
  }
}
