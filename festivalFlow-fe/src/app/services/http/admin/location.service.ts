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
    return this.http.get(ADMIN_BASE_URL + "day/" + dayId);
  }

  getLocationById(param: any): Observable<Location> {
    return this.http.get(ADMIN_BASE_URL + param);
  }

  deleteLocation(location: Location) {
    return this.http.delete(ADMIN_BASE_URL, location);
  }

  createLocation(formData: Location) {
    return this.http.post(ADMIN_BASE_URL + 'create', formData);
  }

  uploadImage(image: File, locationId: number) {
    const formData = new FormData();
    formData.append('image', image);
    return this.http.post(ADMIN_BASE_URL + 'upload-image/' + locationId, formData);
  }

  updateLocation(location: Location) {
    return this.http.put(ADMIN_BASE_URL + 'update', location);
  }
}
