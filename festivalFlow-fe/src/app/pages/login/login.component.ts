import {Component, OnInit} from '@angular/core';
import {NavigationService} from "../../services/navigation/navigation.service";
import {CookiesService} from "../../services/token/cookies.service";
import {TokenService} from "../../services/http/token/token.service";
import {AuthEntity} from "../../interfaces/utility/AuthEntity";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  protected mail: any;
  protected code: any;
  protected isEmailInserted: boolean = false;

  constructor(private navigationService: NavigationService,
              private cookiesService: CookiesService,
              private tokenService: TokenService
  ) {
  }

  ngOnInit() {
    console.log("userId", this.cookiesService.getUserId());
    console.log("token", this.cookiesService.getToken());
    if(this.cookiesService.getUserId() && this.cookiesService.getToken()){
      this.navigationService.goToHome();
    }
  }

  checkCredentials(mail: string): boolean {
    return RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$').test(mail);
  }

  onSubmit() {
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
      this.tokenService.loginConfirm(this.cookiesService.getUserId(), this.code).subscribe(response => {
        if (response !== undefined && response.valid) {
          this.cookiesService.setToken(String(response.token));
          this.navigationService.goToHome()
        }
      });
    }
  }
}
