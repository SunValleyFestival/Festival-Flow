import {Injectable} from '@angular/core';
import {HttpAuthClient} from "../token/http-auth-client";
import {environment} from '../../../../environments/environment';
import {Association} from "../../../interfaces/AssociationEntity";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/association/";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(
    private http: HttpAuthClient,
  ) {
  }

  getAdminAssociationByShiftId(shiftId: number) {
    return this.http.get(ADMIN_BASE_URL + "shift/" + shiftId);
  }

  rejectAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "reject", association);
  }

  approveAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "accept", association);
  }
}
