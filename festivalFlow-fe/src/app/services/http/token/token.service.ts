import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of} from "rxjs";
import {environment} from "../../../../environments/environment";
import {AuthEntity} from "../../../interfaces/utility/AuthEntity";

const BASE_URL = environment.userBaseUrl + "/auth/";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private http: HttpClient) {
  }

  public login(email: string): Observable<AuthEntity> {
    let loginData: AuthEntity = {email};
    return this.http.post<AuthEntity>(BASE_URL + "login", loginData);
  }

  public loginConfirm(userId: number, code: string): Observable<AuthEntity> {
    const loginData: AuthEntity = {userId, code};
    return this.http.post<AuthEntity>(BASE_URL + "login/confirm", loginData);
  }

  public isValidToken(userId: number, token: string): boolean {
    const loginData: AuthEntity = {userId, token};
    let isValid: undefined | boolean = false;

    this.http.post<AuthEntity>(BASE_URL + "validate", loginData).subscribe((response: AuthEntity) => {
      console.log("isValid:", response.valid);
     isValid = response.valid;
    })

    return isValid;
  }


}
