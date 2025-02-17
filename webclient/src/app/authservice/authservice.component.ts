import { Component, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { TranslationService } from '../translation.service';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common'; 
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { SIGNAL_TOKEN } from './signal-token';
import { Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-authservice',
  imports: [ TranslateModule, CommonModule, FormsModule ],
  standalone: true,
  templateUrl: './authservice.component.html',
  styleUrl: './authservice.component.css',
  providers: [ { provide: SIGNAL_TOKEN, useValue: new EventEmitter<string>() } ]  // Biztosítjuk a Signal Token-t
})
export class AuthserviceComponent {
  username: string = '';
  password: string = '';

  @Output() loginSuccess: EventEmitter<string> = new EventEmitter<string>();

  constructor(public translationService: TranslationService,
    private http: HttpClient, 
    @Inject(SIGNAL_TOKEN) private signal: EventEmitter<string>) {}
  
    onSubmit() {
      const loginPayload = { username: this.username, password: this.password };
      this.http.post(environment.authenticationUrl, loginPayload)
      .subscribe((response: any) => {
        console.log('Login Response:', response);
        this.loginSuccess.emit(response.token);
      }, (error) => {
        console.error('Login failed', error);
      });
    }
}
