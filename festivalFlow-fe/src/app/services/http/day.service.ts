import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const BASE_URL = environment.apiUrl + "/association";
@Injectable({
  providedIn: 'root'
})
export class DayService {

  constructor(private http: HttpClient) {
  }

  getAllDays() {
    return this.http.get(BASE_URL + "/all");
  }
}
