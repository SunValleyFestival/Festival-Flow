import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Association, AssociationAdmin} from "../../../interfaces/AssociationEntity";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/association/";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(
    private http: HttpClient,
  ) {
  }

  getAdminAssociationByShiftId(shiftId: number) {
    return this.http.get(ADMIN_BASE_URL + "shift/" + shiftId) as Observable<AssociationAdmin[]>;
  }

  rejectAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "reject", association);
  }

  approveAssociation(association: Association) {
    return this.http.put(ADMIN_BASE_URL + "accept", association);
  }
}
