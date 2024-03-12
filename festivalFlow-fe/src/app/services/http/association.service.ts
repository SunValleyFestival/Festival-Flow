import {Injectable} from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Association} from "../../interfaces/AssociationEntity";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Collaborator} from "../../interfaces/CollaboratorEntity";

const BASE_URL = environment.userBaseUrl + "/association/";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(private http: HttpAuthClient) {
  }

  getAssociations() {
    return this.http.get(BASE_URL) + "all";
  }

  saveAssociation(association: Association) {
    return this.http.post(BASE_URL + "create", association);
  }

  getAssociationByShiftId(shiftId: number | undefined): Observable<Association[]> {
    return this.http.get(BASE_URL + shiftId);
  }

  getCollaboratorsByShiftId(shiftId: number | undefined): Collaborator[] {
    let result: Collaborator[] = [];
    this.http.get(BASE_URL + "collaborators/" + shiftId).pipe().subscribe((collaborators: Collaborator[]) => {
      result = collaborators;
    })

    return result;
  }
}
