import { Component } from '@angular/core';
import {NavigationService} from "../../services/navigation/navigation.service";
import {CookiesService} from "../../services/token/cookies.service";
import {TokenService} from "../../services/http/token/token.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected mail: any;
  protected code: any;
  protected isEmailInserted: boolean = false;

  constructor(private navigationService: NavigationService,
              private cookiesService: CookiesService,
              private tokenService: TokenService
              ) {
  }

  checkCredentials(mail: string): boolean {
    return RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$').test(mail);
  }

  onSubmit() {
    if(this.mail !== undefined && this.code === undefined) {
      if(this.checkCredentials(this.mail)) {
        if(this.tokenService.login(this.mail)) {
          this.isEmailInserted = true;
        }
      }
    } else if(this.mail !== undefined && this.code !== undefined) {
      this.tokenService.loginConfirm(this.mail, this.code);
    }
  }
}
