import {Injectable} from '@angular/core';
import {CookiesService} from "../services/token/cookies.service";
import {TokenService} from "../services/http/token/token.service";
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

  async isAuthenticated() {
    let token = this.cookieService.getToken();
    let userId: number = Number(this.cookieService.getUserId());
    let isValid = await this.tokenService.isValidToken(userId, token);

    console.log("isValid qui", isValid);
    return isValid;
  }
}
