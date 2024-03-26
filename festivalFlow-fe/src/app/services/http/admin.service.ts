import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpAuthClient} from "./token/http-auth-client";
import {EmailRequest} from "../../interfaces/EmailRequestEntity";

const ADMIN_BASE_URL = environment.adminBaseUrl;

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpAuthClient) {
  }

  exportByMail(email: EmailRequest){
    return this.http.post(ADMIN_BASE_URL + "/export", email);
  }

  getLockStatus(){
    return this.http.get(ADMIN_BASE_URL + "/lock/get");
  }

  setLockStatus(status: boolean){
    return this.http.post(ADMIN_BASE_URL + "/lock", status);
  }

}
