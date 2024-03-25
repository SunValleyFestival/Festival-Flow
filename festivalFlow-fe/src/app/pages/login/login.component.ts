import {Component, OnInit} from '@angular/core';
import {NavigationService} from "../../services/navigation/navigation.service";
import {CookiesService} from "../../services/token/cookies.service";
import {TokenService} from "../../services/http/token/token.service";
import {AuthEntity} from "../../interfaces/utility/AuthEntity";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  protected isEmailInserted: boolean = false;
  protected isDateInserted: boolean = false;
  protected buttonDisabled: boolean = false;
  protected loginError: boolean = false;
  protected errorDescription: string = '';

  protected loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    date: ['', Validators.required],
    code: ['', Validators.required],
  });


  constructor(private navigationService: NavigationService,
              private cookiesService: CookiesService,
              private tokenService: TokenService,
              private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    if (this.cookiesService.getUserId() && this.cookiesService.getToken()) {
      this.navigationService.goToHome();
    }
  }

  setMail() {
    this.buttonDisabled = true;

    let authEntity: AuthEntity = {
      email: this.loginForm.value.email ? this.loginForm.value.email : ''
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
        } else {
          this.buttonDisabled = false;
          this.showError('Errore interno per favore riprovare più tardi');
        }
      }
    );
  }

  setDate() {
    this.buttonDisabled = true;
    let authEntity: AuthEntity = {
      email: this.loginForm.value.email ? this.loginForm.value.email : '',
      date: this.loginForm.value.date ? this.loginForm.value.date : ''
    };

    this.tokenService.login(authEntity).pipe().subscribe(
      response => {
        if (response !== undefined) {
          authEntity = response;
          this.cookiesService.setUserId(String(authEntity.userId));
          this.isDateInserted = true;
          this.buttonDisabled = false;
        } else {
          this.buttonDisabled = false;
          this.showError('Errore interno per favore riprovare più tardi');
        }
      }
    );

  }

  login() {
    this.buttonDisabled = true;
    this.tokenService.loginConfirm(this.cookiesService.getUserId(), this.loginForm.value.code ? this.loginForm.value.code : '').subscribe(response => {
      if (response !== undefined) {
        if (response.valid) {
          this.cookiesService.setToken(String(response.token));
          this.buttonDisabled = false;
          this.navigationService.goToHome();
        } else {
          this.buttonDisabled = false;
          this.showError('Codice inserito non valido');
        }
      } else {
        this.buttonDisabled = false;
        this.showError('Errore interno per favore riprovare più tardi');
      }
    });
  }

  showError(error: string) {
    this.errorDescription = error;
    this.loginError = true;
    setTimeout(() => {
      this.loginError = false;
    }, 3000);
  }
}
