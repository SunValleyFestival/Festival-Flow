import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {EmailRequest} from "../../../interfaces/EmailRequestEntity";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const ADMIN_BASE_URL = environment.adminBaseUrl;

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) {
  }

  exportByMail(email: EmailRequest){
    return this.http.post(ADMIN_BASE_URL + "/export", email);
  }

  getLockStatus(){
    return this.http.get(ADMIN_BASE_URL + "/lock/get") as Observable<boolean>;
  }

  setLockStatus(status: boolean){
    return this.http.post(ADMIN_BASE_URL + "/lock", status) as Observable<boolean>;
  }

}
