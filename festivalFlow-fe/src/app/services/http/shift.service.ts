import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const BASE_URL = environment.baseUrl + "/shift";
@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  constructor(private http: HttpClient) {
  }

  getAllShifts() {
    return this.http.get(BASE_URL + "/all");
  }

}
