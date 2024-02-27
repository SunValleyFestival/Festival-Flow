import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";
import {Day} from "../../interfaces/DayEntity";

const BASE_URL = environment.baseUrl + "/day";
@Injectable({
  providedIn: 'root'
})
export class DayService {

  constructor(private http: HttpClient) {
  }

  getAllDays(): Observable<Day[]> {
    return this.http.get<Day[]>(BASE_URL + "/");
  }

  getDayById(param: any): Observable<Day> {
    return this.http.get<Day>(BASE_URL + "/" + param);
  }
}
