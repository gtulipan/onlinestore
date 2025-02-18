import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../environments/environment';

export interface CsrfToken {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class CsrfService {
  constructor(private http: HttpClient) {}

  getCsrfToken(): Observable<CsrfToken> {
    return this.http.get<CsrfToken>(environment.csrfTokenUrl, { withCredentials: true }).pipe(
      map((response: CsrfToken) => {
        if (response && response.token) {
          return response;
        } else {
          throw new Error('CSRF token not found');
        }
      })
    );
  }
}

