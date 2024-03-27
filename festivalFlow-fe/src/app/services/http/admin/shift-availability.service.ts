import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpAuthClient} from "../token/http-auth-client";
import {Observable} from "rxjs";
import {ShiftAvailability} from "../../../interfaces/ShiftAvailabilityView";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/shift-availability/";

@Injectable({
  providedIn: 'root'
})
export class ShiftAvailabilityService {

  constructor(private http: HttpAuthClient) {
  }

  getAvailableSlotsByLocationId(locationId: number | undefined): Observable<ShiftAvailability> {
    return this.http.get(ADMIN_BASE_URL + "location/" + locationId);
  }
}
