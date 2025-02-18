import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CsrfService, CsrfToken } from '../csrf.service';
import { TranslationService } from '../translation.service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-authservice',
  templateUrl: './authservice.component.html',
  styleUrls: ['./authservice.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class AuthserviceComponent implements OnInit {
  username: string = '';
  password: string = '';
  loading: boolean = false;

  constructor(
    private http: HttpClient,
    private csrfService: CsrfService,
    public translationService: TranslationService
  ) {}

  ngOnInit(): void {
    // Kérjük a CSRF tokent, amikor az alkalmazás inicializálódik
    this.csrfService.getCsrfToken().subscribe({
      next: (token: CsrfToken) => {
        console.log('Generated CSRF Token:', token.token);
        document.cookie = `XSRF-TOKEN=${token.token}; Path=/`;
      },
      error: (error: HttpErrorResponse) => {
        console.log('Failed to get CSRF token', error);
      }
    });
  }

  onSubmit() {
    this.loading = true;
    // A login kérés végrehajtása
    const loginData = { username: this.username, password: this.password };
    this.http.post(environment.authenticationUrl, loginData, {
      withCredentials: true // Beállítjuk a withCredentials flaget
    }).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.loading = false;
      },
      error: (error: HttpErrorResponse) => {
        console.log('Login failed', error);
        this.loading = false;
      }
    });
  }
}
