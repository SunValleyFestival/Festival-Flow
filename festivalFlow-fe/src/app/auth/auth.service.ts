import { Injectable } from '@angular/core';
import {CookiesService} from "../services/token/cookies.service";
import {TokenService} from "../services/http/token/token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = true;
  isAdmin = true;

  constructor(private cookieService: CookiesService,
              private tokenService: TokenService
              ) { }

  isAuthenticated() {
    let token = this.cookieService.getToken();
    let userId: number = Number(this.cookieService.getUserId());
    return this.tokenService.isValidToken(userId, token);
  }

  isRoleAdmin() {
    return this.isAdmin;
  }

  setLoggedIn(isLoggedIn: boolean) {
    this.isLoggedIn = isLoggedIn;
  }
}
