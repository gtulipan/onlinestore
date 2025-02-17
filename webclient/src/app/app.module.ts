import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateModule, TranslateLoader, TranslateService, MissingTranslationHandler } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { FormsModule } from '@angular/forms';
import { CustomMissingTranslationHandler } from './missing-translation.handler';
import { TranslationService } from './translation.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CsrfInterceptor } from './http-interceptors/csrf-interceptor.interceptor';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { AppComponent } from './app.component'; // Importálva
import { AuthserviceComponent } from './authservice/authservice.component'; // Importálva
import { environment } from '../environments/environment'; // Importáljuk az environment változót

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, environment.apiUrl, '.json');
}

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppComponent, // Importált standalone komponens
    AuthserviceComponent, // Importált standalone komponens
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      missingTranslationHandler: {
        provide: MissingTranslationHandler,
        useClass: CustomMissingTranslationHandler
      }
    })
  ],
  providers: [
    TranslateService,
    TranslationService,
    provideHttpClient(withFetch(), withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true }
  ]
})
export class AppModule { }
