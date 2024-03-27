import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Shift} from "../../../interfaces/ShiftEntity";
import {HttpAuthClient} from "../token/http-auth-client";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/shift/";

@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  constructor(private http: HttpAuthClient) {
  }

  getShiftsByLocationId(locationId: number): Observable<Shift[]> {
    return this.http.get(ADMIN_BASE_URL + "location/" + locationId);
  }

  deleteShift(shift: Shift) {
    return this.http.delete(ADMIN_BASE_URL, shift);
  }

  createShift(shift: Shift) {
    return this.http.post(ADMIN_BASE_URL + 'create', shift);
  }

  getShiftById(shiftId: number) {
    return this.http.get(BASE_URL + shiftId);
  }
}
