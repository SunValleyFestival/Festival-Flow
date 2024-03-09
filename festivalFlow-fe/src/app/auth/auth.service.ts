import {Injectable} from '@angular/core';
import {CookiesService} from "../services/token/cookies.service";
import {TokenService} from "../services/http/token/token.service";
import {AuthEntity} from "../interfaces/utility/AuthEntity";
import {NavigationService} from "../services/navigation/navigation.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = true;
  isAdmin = true;

  constructor(private cookieService: CookiesService,
              private tokenService: TokenService,
              private navigationService: NavigationService
  ) {
  }

  isAuthenticated() {
    let token = this.cookieService.getToken();
    let userId: number = Number(this.cookieService.getUserId());
     this.tokenService.isValidToken(userId, token);
     return true;
  }
}
