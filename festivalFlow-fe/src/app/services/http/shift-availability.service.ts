import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const BASE_URL = environment.baseUrl + "/shift-availability/";

@Injectable({
  providedIn: 'root'
})
export class ShiftAvailabilityService {

  constructor(private http: HttpClient) {
  }

  getShiftAvailability(shiftId: number): Observable<number> {
    return this.http.get<number>(BASE_URL + shiftId);
  }

  getAvailableSlotsByLocationId(locationId: number): Observable<number> {
    return this.http.get<number>(BASE_URL + "/location/" + locationId);
  }
}
