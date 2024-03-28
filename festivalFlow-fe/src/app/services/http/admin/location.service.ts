import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Location} from "../../../interfaces/LocationEntity";
import {HttpClient} from "@angular/common/http";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/location/";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) {
  }

  getLocationsByDayId(dayId: number) {
    return this.http.get(ADMIN_BASE_URL + "day/" + dayId);
  }

  getLocationById(param: any) {
    return this.http.get(ADMIN_BASE_URL + param) as Observable<Location>;
  }

  deleteLocation(location: Location) {
    return this.http.delete(ADMIN_BASE_URL, {
      body: location
    });
  }

  createLocation(formData: Location) {
    return this.http.post(ADMIN_BASE_URL + 'create', formData) as Observable<Location>;
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
