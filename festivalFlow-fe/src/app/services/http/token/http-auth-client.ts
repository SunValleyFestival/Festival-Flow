import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CookiesService} from "../../token/cookies.service";
import {Observable} from "rxjs";

@Injectable()
export class HttpAuthClient {

  constructor(private http: HttpClient,
              private cookiesService: CookiesService,
              ) {}


  get(url: string): Observable<any> {
    let headers = new HttpHeaders({'Authorization': 'Bearer ' + this.cookiesService.getToken()});
    return this.http.get(url, {
      headers: headers
    });
  }

  post(url: string, data: any): any {
    let headers = new HttpHeaders({'Authorization': 'Bearer ' + this.cookiesService.getToken()});
    return this.http.post(url, data, {
      headers: headers,
      observe: 'response'
    });
  }

  put(url: string, data: any): any {
    let headers = new HttpHeaders({'Authorization': 'Bearer ' + this.cookiesService.getToken()});
    return this.http.put(url, data, {
      headers: headers,
      observe: 'response'
    });
  }

  delete(url: string, data: any): any {
    let headers = new HttpHeaders({'Authorization': 'Bearer ' + this.cookiesService.getToken()});
    return this.http.delete(url, {
      headers: headers,
      body: data,
      observe: 'response'
    });
  }
}
