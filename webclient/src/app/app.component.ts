import { Component, OnInit, inject, Inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslationService } from './translation.service';
import { AuthserviceComponent } from './authservice/authservice.component';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    TranslateModule, 
    CommonModule, 
    AuthserviceComponent,
    FormsModule
  ],
  standalone: true
})
export class AppComponent implements OnInit {
  translationService = inject(TranslationService);
  translateService = inject(TranslateService);

  constructor(@Inject(HttpClient) private http: HttpClient) {}

  ngOnInit() {
    this.translationService.updateTranslations();  
  }

  changeLanguage(lang: string) {
    this.translationService.changeLanguage(lang);
  }
}
