import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const BASE_URL = environment.apiUrl + "/location";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get(BASE_URL + "/all");
  }

}
