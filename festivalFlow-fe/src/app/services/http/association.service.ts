import {Injectable} from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Association} from "../../interfaces/AssociationEntity";

const BASE_URL = environment.userBaseUrl + "/association/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/association/";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(
    private http: HttpAuthClient,
  ) {
  }

  saveAssociation(association: Association) {
    return this.http.post(BASE_URL + "create", association);
  }

  getAdminAssociationByShiftId(shiftId: number) {
    return this.http.get(ADMIN_BASE_URL + "collaborator/" + shiftId);
  }

  rejectAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "reject", association);
  }

  approveAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "approve", association);
  }
}
