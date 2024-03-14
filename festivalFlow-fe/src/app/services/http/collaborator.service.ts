import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {HttpAuthClient} from "./token/http-auth-client";
import {Observable} from "rxjs";

const BASE_URL = environment.userBaseUrl + "/collaborator/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/collaborator/";
@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpAuthClient) {
  }

  getCollaborators(): Observable<Collaborator[]>{
    return this.http.get(ADMIN_BASE_URL);
  }

  updateCollaborator(collaborator: Collaborator) {
    return this.http.put(BASE_URL + "update", collaborator);
  }

  getCollaboratorById(id: number): Observable<Collaborator> {
    return this.http.get(BASE_URL + id);
  }

  deleteCollaborator(collaborator: Collaborator) {
    return this.http.delete(BASE_URL + "delete/", collaborator);
  }
}
