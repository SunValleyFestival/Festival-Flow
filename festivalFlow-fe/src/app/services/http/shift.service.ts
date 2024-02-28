import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from "rxjs";
import {Shift} from "../../interfaces/ShiftEntity";

const BASE_URL = environment.baseUrl + "/shift/";

@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  constructor(private http: HttpClient) {
  }

  getAllShifts() {
    return this.http.get(BASE_URL);

  }

  getShiftsByLocationId(locationId: number): Observable<Shift[]> {
    return this.http.get<Shift[]>(BASE_URL + "/location/" + locationId);
  }

  getShiftByLocationIdAndDay(locationId: number | undefined, day: number | undefined): Observable<Shift[]> {
    console.log(BASE_URL + "location/" + locationId + "/" + day)
    return this.http.get<Shift[]>(BASE_URL + "location/" + locationId + "/" + day);
  }
}
