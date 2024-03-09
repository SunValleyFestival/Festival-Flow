import {Injectable} from '@angular/core';
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class CookiesService {

  constructor(
    private cookieService: CookieService
  ) {
  }

  setUsername(username: string) {
    this.cookieService.set('username', username);
  }

  getUsername() {
    return this.cookieService.get('username');
  }

  setToken(token: string) {
    this.cookieService.set('token', token);
  }

  getToken() {
    return this.cookieService.get('token');
  }
}