import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ShiftAvailability} from "../../interfaces/ShiftAvailabilityView";

const BASE_URL = environment.baseUrl + "/shift-availability/";

@Injectable({
  providedIn: 'root'
})
export class ShiftAvailabilityService {

  constructor(private http: HttpClient) {
  }

  getShiftAvailability(shiftId: number | undefined): Observable<ShiftAvailability> {
    return this.http.get<ShiftAvailability>(BASE_URL + shiftId);
  }

  getAvailableSlotsByLocationId(locationId: number | undefined): Observable<ShiftAvailability> {
    return this.http.get<ShiftAvailability>(BASE_URL + "/location/" + locationId);
  }
}
