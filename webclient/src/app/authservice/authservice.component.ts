import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CsrfService, CsrfToken } from '../csrf.service';
import { TranslationService } from '../translation.service';
import { environment } from '../../environments/environment';
import { jwtDecode } from 'jwt-decode'; //npm install jwt-decode

interface AuthResponse {
  token: string;
}

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
    this.http.post<AuthResponse>(environment.authenticationUrl, loginData, {
      withCredentials: true // Beállítjuk a withCredentials flaget
    }).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        if (response && response.token) {
          localStorage.setItem('token', response.token);
          this.checkToken();
          console.log('Token saved to localStorage');
        } else {
          console.log('Token not found in response');
        }
        this.loading = false;
      },
      error: (error: HttpErrorResponse) => {
        console.log('Login failed', error);
        this.loading = false;
      }
    });
  }

  checkToken() {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded: any = jwtDecode(token);
      console.log('Jogosultságok:', decoded.roles);
      console.log('Lejárati idő:', decoded.exp);

      // Ellenőrizni, hogy lejárt-e
      const currentTime = Math.floor(Date.now() / 1000); // UNIX idő másodpercekben
      if (decoded.exp < currentTime) {
        console.log('A token lejárt.');
      } else {
        console.log('A token érvényes.');
      }
    }
  }
}
