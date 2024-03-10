import { Injectable } from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";

const BASE_URL = environment.userBaseUrl + "/location/";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpAuthClient) {
  }

  getAll() {
    return this.http.get(BASE_URL);
  }

  getLocationsByDayId(dayId: number): Observable<Location[]> {
    return this.http.get(BASE_URL + "day/" + dayId);
  }

  getLocationById(param: any): Observable<Location> {
    return this.http.get(BASE_URL + param);
  }
}
