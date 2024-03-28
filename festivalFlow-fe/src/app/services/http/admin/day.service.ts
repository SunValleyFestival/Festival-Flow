import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from "rxjs";
import {Day} from "../../../interfaces/DayEntity";
import {HttpClient} from "@angular/common/http";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/day/";

@Injectable({
  providedIn: 'root'
})
export class DayService {

  constructor(private http: HttpClient) {
  }

  getAllDays() {
    return this.http.get(ADMIN_BASE_URL) as Observable<Day[]>;
  }

  saveDay(day: Day) {
    return this.http.post(ADMIN_BASE_URL + 'create', day);
  }

  deleteDayById(currentDayId: number) {
    return this.http.delete(ADMIN_BASE_URL + currentDayId);
  }
}
