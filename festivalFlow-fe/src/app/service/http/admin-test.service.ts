import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

const BASE_URL = 'http://localhost:8080/admin'

@Injectable({
  providedIn: 'root'
})
export class AdminTestService {

  constructor(private httpClient: HttpClient) { }

  getTest(){
    return this.httpClient.get(BASE_URL);
  }
}
