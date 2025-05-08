import {Injectable} from '@angular/core';
import {HttpAuthClient} from "../token/http-auth-client";
import {environment} from '../../../../environments/environment';
import {Association} from "../../../interfaces/AssociationEntity";
import {Observable} from "rxjs";

const BASE_URL = environment.userBaseUrl + "/association/";

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

  getAssociationByCollaboratorId(collaboratorId: number) {
    return this.http.get(BASE_URL + "collaborator-id/" + collaboratorId);
  }

  getCollaboratorsNames(shiftId: number) {
    return this.http.get(BASE_URL + "collaborators/" + shiftId) as Observable<string[]>;
  }

  getAssociations() {
    return this.http.get(BASE_URL) as Observable<Association[]>;
  }
}
