import { Injectable } from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";
import {Location} from "../../interfaces/LocationEntity";

const BASE_URL = environment.userBaseUrl + "/location/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/location/";

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

  deleteLocation(location: Location) {
    console.log("path", BASE_URL)
    console.log("deleteLocation", location);
    return this.http.delete(ADMIN_BASE_URL, location);
  }
}
