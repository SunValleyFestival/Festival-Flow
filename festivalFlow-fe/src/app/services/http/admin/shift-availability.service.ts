import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/shift-availability/";

@Injectable({
  providedIn: 'root'
})
export class ShiftAvailabilityService {

  constructor(private http: HttpClient) {
  }

  getAvailableSlotsByLocationId(locationId: number | undefined) {
    return this.http.get(ADMIN_BASE_URL + "location/" + locationId);
  }
}
