import {Injectable} from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Association} from "../../interfaces/AssociationEntity";
import {Observable} from "rxjs";

const BASE_URL = environment.userBaseUrl + "/association/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/association/";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(private http: HttpAuthClient) {
  }

  saveAssociation(association: Association) {
    return this.http.post(BASE_URL + "create", association);
  }

  getAssociationByShiftId(shiftId: number | undefined): Observable<Association[]> {
    return this.http.get(BASE_URL + shiftId);
  }

  rejectAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "reject", association);
  }

  approveAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "approve", association);
  }
}
