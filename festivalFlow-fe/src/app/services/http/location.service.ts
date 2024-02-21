import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";

const BASE_URL = environment.baseUrl + "/location/";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get(BASE_URL);
  }

  getLocationsByDayId(dayId: number): Observable<Location[]> {
    return this.http.get<Location[]>(BASE_URL + "day/" + dayId);
  }

}
