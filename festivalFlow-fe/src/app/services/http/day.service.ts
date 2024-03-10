import { Injectable } from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";
import {Day} from "../../interfaces/DayEntity";

const BASE_URL = environment.baseUrl + "/day";
@Injectable({
  providedIn: 'root'
})
export class DayService {

  constructor(private http: HttpAuthClient) {
  }

  getAllDays(): Observable<Day[]> {
    return this.http.get(BASE_URL + "/");
  }

  getDayById(param: any): Observable<Day> {
    return this.http.get(BASE_URL + "/" + param);
  }
}
