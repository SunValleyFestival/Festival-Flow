import {Component} from '@angular/core';
import {NavigationService} from "../../services/navigation/navigation.service";
import {CookiesService} from "../../services/token/cookies.service";
import {TokenService} from "../../services/http/token/token.service";
import {AuthEntity} from "../../interfaces/utility/AuthEntity";

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
    console.log(this.mail, this.code)
    if (this.mail !== undefined && this.code === undefined) {
      if (this.checkCredentials(this.mail)) {
        let authEntity: AuthEntity;
        this.tokenService.login(this.mail).subscribe(
          response => {
            console.log(response)
            if (response !== undefined) {
              authEntity = response;
              this.isEmailInserted = true;
              this.cookiesService.setUserId(String(authEntity.userId));
            }
          }
        );

      }
    } else if (this.mail !== undefined && this.code !== undefined) {
      let authEntity = this.tokenService.loginConfirm(this.mail, this.code);
      if (authEntity !== undefined) {
        this.cookiesService.setToken(String(authEntity.token));
        this.navigationService.goToHome()
      }
    }
  }
}
