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
  protected date: any;
  protected isEmailInserted: boolean = false;
  protected isDateInserted: boolean = false;
  protected buttonDisabled: boolean = false;

  constructor(private navigationService: NavigationService,
              private cookiesService: CookiesService,
              private tokenService: TokenService
  ) {
  }

  ngOnInit() {
    console.log("userId", this.cookiesService.getUserId());
    console.log("token", this.cookiesService.getToken());
    if (this.cookiesService.getUserId() && this.cookiesService.getToken()) {
      this.navigationService.goToHome();
    }
  }

  checkCredentials(mail: string): boolean {
    return RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$').test(mail);
  }

  setMail() {
    if (this.checkCredentials(this.mail)) {
      this.buttonDisabled = true;

      let authEntity: AuthEntity = {
        email: this.mail
      };

      this.tokenService.loginMail(authEntity).pipe().subscribe(
        response => {
          if (response !== undefined) {
            if (response.valid) {
              this.tokenService.login(authEntity).pipe().subscribe(
                response => {
                  if (response !== undefined) {
                    authEntity = response;
                    this.cookiesService.setUserId(String(authEntity.userId));
                    this.isDateInserted = true;
                    this.isEmailInserted = true;
                    this.buttonDisabled = false;
                  }
                }
              );
            } else {
              this.buttonDisabled = false;
              this.isEmailInserted = true;
            }
          }
        }
      );
    }
  }

  setDate() {
    this.buttonDisabled = true;
    let authEntity: AuthEntity = {
      email: this.mail,
      date: this.date
    };

    this.tokenService.login(authEntity).pipe().subscribe(
      response => {
        if (response !== undefined) {
          authEntity = response;
          this.cookiesService.setUserId(String(authEntity.userId));
          this.isDateInserted = true;
          this.buttonDisabled = false;
        }
      }
    );

  }

  login() {
    this.buttonDisabled = true;
    this.tokenService.loginConfirm(this.cookiesService.getUserId(), this.code).subscribe(response => {
      if (response !== undefined && response.valid) {
        this.cookiesService.setToken(String(response.token));
        this.buttonDisabled = false;
        this.navigationService.goToHome();
      } else {
        this.buttonDisabled = false;
      }
    });
  }
}
