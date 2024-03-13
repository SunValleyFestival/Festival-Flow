import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {AuthEntity} from "../../../interfaces/utility/AuthEntity";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {CookiesService} from "../../token/cookies.service";

const BASE_URL = environment.userBaseUrl + "/auth/";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(
    private http: HttpClient,
    private cookiesService: CookiesService,
  ) {
  }

  public login(email: string): Observable<AuthEntity> {
    let loginData: AuthEntity = {email};
    return this.http.post<AuthEntity>(BASE_URL + "login", loginData);
  }

  public loginConfirm(userId: number, code: string): Observable<AuthEntity> {
    const loginData: AuthEntity = {userId, code};
    return this.http.post<AuthEntity>(BASE_URL + "login/confirm", loginData);
  }

  isValidToken(userId: number, token: string): Promise<boolean> {
    const loginData: AuthEntity = {userId, token};
    return new Promise((resolve, reject) => {
      this.http.post<AuthEntity>(BASE_URL + "validate", loginData).pipe().subscribe(response => {
        if (response.valid !== undefined) {
          resolve(response.valid);
        }
      }, error => {
        reject(error);
      });
    });
  }

  getCollaboratorFromToken(token: string): Observable<Collaborator> {
    let headers = new HttpHeaders({'Authorization': 'Bearer ' + this.cookiesService.getToken()});
    return this.http.get<Collaborator>(BASE_URL + "collaborator/" + token,
      {
        headers: headers
      }
    )
  }
}
