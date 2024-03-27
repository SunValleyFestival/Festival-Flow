import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpAuthClient} from "../token/http-auth-client";
import {Observable} from "rxjs";
import {ShiftAvailability} from "../../../interfaces/ShiftAvailabilityView";

const BASE_URL = environment.userBaseUrl + "/shift-availability/";

@Injectable({
  providedIn: 'root'
})
export class ShiftAvailabilityService {

  constructor(private http: HttpAuthClient) {
  }

  getShiftAvailability(shiftId: number | undefined): Observable<Number> {
    return this.http.get(BASE_URL + shiftId);
  }

  getAvailableSlotsByLocationId(locationId: number | undefined): Observable<ShiftAvailability> {
    return this.http.get(BASE_URL + "location/" + locationId);
  }
}
