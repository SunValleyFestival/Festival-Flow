import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CookiesService} from "../../token/cookies.service";

@Injectable()
export class HttpAuthClient {

  constructor(private http: HttpClient,
              private cookiesService: CookiesService,
              ) {}


  get(url: string): any {
    let headers = new HttpHeaders();
    let authString = 'Bearer ' + this.cookiesService.getToken();
    headers.set('Authorization', authString);
    return this.http.get(url, {
      headers: headers
    });
  }

  post(url: string, data: any): any {
    let headers = new HttpHeaders();
    let authString = 'Bearer ' + this.cookiesService.getToken();
    headers.set('Authorization', authString);
    return this.http.post(url, data, {
      headers: headers
    });
  }
}
