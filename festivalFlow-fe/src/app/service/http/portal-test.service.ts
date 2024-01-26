import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

const BASE_URL = 'http://localhost:8080/portal'

@Injectable({
  providedIn: 'root'
})
export class PortalTestService {

  constructor(private httpClient: HttpClient) {
  }

  getTest() {
    return this.httpClient.get(BASE_URL);
  }
}
