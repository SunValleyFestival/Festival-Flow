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

  setUserId(userId: string) {
    this.cookieService.set('userId', userId);
  }

  getUserId(): number {
    return Number(this.cookieService.get('userId'));
  }

  setToken(token: string) {
    this.cookieService.set('token', token);
  }

  getToken() {
    return this.cookieService.get('token');
  }
}
