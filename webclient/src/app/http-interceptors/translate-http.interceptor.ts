import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class TranslateHttpInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (request.url.includes(environment.apiSubLink)) {
      const clonedRequest = request.clone({
        setHeaders: {
          'Accept': 'application/json'
        }
      });
      return next.handle(clonedRequest);
    }

    return next.handle(request);
  }
}
