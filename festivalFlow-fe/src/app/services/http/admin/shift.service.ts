import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Shift} from "../../../interfaces/ShiftEntity";
import {HttpClient} from "@angular/common/http";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/shift/";

@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  constructor(private http: HttpClient) {
  }

  getShiftsByLocationId(locationId: number) {
    return this.http.get(ADMIN_BASE_URL + "location/" + locationId) as Observable<Shift[]>;
  }

  deleteShift(shift: Shift) {
    return this.http.delete(ADMIN_BASE_URL, {
      body: shift
    });
  }

  createShift(shift: Shift) {
    return this.http.post(ADMIN_BASE_URL + 'create', shift);
  }
}
