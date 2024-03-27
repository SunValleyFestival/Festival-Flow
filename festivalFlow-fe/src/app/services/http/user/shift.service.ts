import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Shift} from "../../../interfaces/ShiftEntity";
import {HttpAuthClient} from "../token/http-auth-client";

const BASE_URL = environment.userBaseUrl + "/shift/";

@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  constructor(private http: HttpAuthClient) {
  }

  getShiftsByLocationId(locationId: number): Observable<Shift[]> {
    return this.http.get(BASE_URL + "location/" + locationId);
  }

  getShiftById(shiftId: number): Observable<Shift> {
    return this.http.get(BASE_URL + shiftId);
  }

}