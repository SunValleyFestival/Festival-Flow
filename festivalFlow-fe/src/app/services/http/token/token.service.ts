import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of} from "rxjs";
import {environment} from "../../../../environments/environment";
import {AuthEntity} from "../../../interfaces/utility/AuthEntity";

const BASE_URL = environment.baseUrl + "/auth/";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private http: HttpClient) {
  }

  public login(email: string): AuthEntity {
    let loginData: AuthEntity = {email};
    let responseData: AuthEntity = {};

    this.http.post<AuthEntity>(BASE_URL + "login", loginData)
      .pipe(
        map(response => {
          responseData = response
        }),
        catchError(error => {
          console.error('Error during login:', error);
          return of(false);
        })
      );
    return responseData;
  }

  public loginConfirm(userId: number, code: string): AuthEntity{
    const loginData: AuthEntity = {userId, code};
    let authEntity: AuthEntity = {};
    this.http.post<AuthEntity>(BASE_URL + "login/confirm", loginData)
      .pipe(
        map(response => {
          authEntity = response
        }),
        catchError(error => {
          console.error('Error during login confirmation:', error);
          return of(undefined);
        })
      );
    return authEntity;
  }

  public isValidToken(userId: number, token: string): boolean {
    const loginData: AuthEntity = {userId, token};
    this.http.post<boolean>(BASE_URL + "validate", loginData)
      .pipe(
        map(response => {
          return response
        }),
        catchError(error => {
          console.error('Error during token validation:', error);
          return of(false);
        })
      );
    return false;
  }



}
