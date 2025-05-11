import {Injectable} from '@angular/core';
import {HttpAuthClient} from "../token/http-auth-client";
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Location} from "../../../interfaces/LocationEntity";

const BASE_URL = environment.userBaseUrl + "/location/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/location/";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpAuthClient) {
  }

  getLocationsByDayId(dayId: number): Observable<Location[]> {
    return this.http.get(BASE_URL + "day/" + dayId);
  }

  getLocationById(param: any): Observable<Location> {
    return this.http.get(BASE_URL + param);
  }

  getLocations() {
    return this.http.get(BASE_URL) as Observable<Location[]>;
  }
}
