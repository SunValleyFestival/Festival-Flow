import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {HttpAuthClient} from "../token/http-auth-client";
import {Observable} from "rxjs";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/collaborator/";

@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpAuthClient) {
  }


  getCollaborators(): Observable<Collaborator[]> {
    return this.http.get(ADMIN_BASE_URL);
  }

  updateCollaboratorFromAdmin(collaborator: Collaborator) {
    return this.http.put(ADMIN_BASE_URL + "update", collaborator);
  }

  getCollaboratorById(id: number): Observable<Collaborator> {
    return this.http.get(ADMIN_BASE_URL + id);
  }

  deleteCollaborator(collaborator: Collaborator) {
    return this.http.delete(ADMIN_BASE_URL, collaborator);
  }
}
