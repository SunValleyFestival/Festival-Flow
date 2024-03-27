import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {HttpAuthClient} from "../token/http-auth-client";

const BASE_URL = environment.userBaseUrl + "/collaborator/";
const ADMIN_BASE_URL = environment.adminBaseUrl + "/collaborator/";

@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpAuthClient) {
  }

  updateCollaborator(collaborator: Collaborator) {
    return this.http.put(BASE_URL + "update", collaborator);
  }

  getCollaboratorFromToken() {
    return this.http.get(BASE_URL);
  }
}
