import { enableProdMode } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateService, TranslateLoader, TranslateCompiler, TranslateStore, TranslateDefaultParser, TranslateParser, MissingTranslationHandler, USE_DEFAULT_LANG, ISOLATE_TRANSLATE_SERVICE, USE_EXTEND, DEFAULT_LANGUAGE } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { FormsModule } from '@angular/forms';
import { CsrfInterceptor } from './app/http-interceptors/csrf-interceptor.interceptor';
import { TranslateMessageFormatCompiler } from "ngx-translate-messageformat-compiler";
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { CustomMissingTranslationHandler } from './app/missing-translation.handler';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, environment.apiUrl, '.json');
}

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch(), withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true },
    {
      provide: TranslateLoader,
      useFactory: HttpLoaderFactory,
      deps: [HttpClient]
    },
    TranslateService,
    TranslateStore,
    { provide: TranslateCompiler, useClass: TranslateMessageFormatCompiler },
    { provide: TranslateParser, useClass: TranslateDefaultParser },
    { provide: MissingTranslationHandler, useClass: CustomMissingTranslationHandler },
    { provide: USE_DEFAULT_LANG, useValue: true },
    { provide: ISOLATE_TRANSLATE_SERVICE, useValue: false },
    { provide: USE_EXTEND, useValue: false },
    { provide: DEFAULT_LANGUAGE, useValue: 'en' }
  ]
}).catch(err => console.error(err));
