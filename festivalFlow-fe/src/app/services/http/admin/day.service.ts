import {Injectable} from '@angular/core';
import {HttpAuthClient} from "../token/http-auth-client";
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Day} from "../../../interfaces/DayEntity";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/day/";

@Injectable({
  providedIn: 'root'
})
export class DayService {

  constructor(private http: HttpAuthClient) {
  }

  getAllDays(): Observable<Day[]> {
    return this.http.get(ADMIN_BASE_URL);
  }

  saveDay(day: Day) {
    return this.http.post(ADMIN_BASE_URL + 'create', day);
  }

  deleteDayById(currentDayId: number) {
    return this.http.delete(ADMIN_BASE_URL + currentDayId, null);
  }
}
