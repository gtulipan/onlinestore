import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CsrfInterceptor implements HttpInterceptor {

  constructor() {
    console.log('Interceptor initialized');
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Intercept method called');
    const csrfToken = this.getCsrfTokenFromCookie();
    console.log('CSRF Token:', csrfToken);
    if (csrfToken) {
      const clonedRequest = req.clone({
        headers: req.headers.set('XSRF-TOKEN', csrfToken),
        withCredentials: true // Beállítjuk a withCredentials flaget
      });
      return next.handle(clonedRequest);
    } else {
      return next.handle(req);
    }
  }

  private getCsrfTokenFromCookie(): string | null {
    const matches = document.cookie.match(new RegExp('(^| )XSRF-TOKEN=([^;]+)'));
    console.log('Cookies:', document.cookie);
    return matches ? decodeURIComponent(matches[2]) : null;
  }
}
