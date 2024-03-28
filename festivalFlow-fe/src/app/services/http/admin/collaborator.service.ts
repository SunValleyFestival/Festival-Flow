import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

const ADMIN_BASE_URL = environment.adminBaseUrl + "/collaborator/";

@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpClient) {
  }


  getCollaborators() {
    return this.http.get(ADMIN_BASE_URL) as Observable<Collaborator[]>;
  }

  updateCollaboratorFromAdmin(collaborator: Collaborator) {
    return this.http.put(ADMIN_BASE_URL + "update", collaborator);
  }

  getCollaboratorById(id: number) {
    return this.http.get(ADMIN_BASE_URL + id) as Observable<Collaborator>;
  }

  deleteCollaborator(collaborator: Collaborator) {
    return this.http.delete(ADMIN_BASE_URL, {
      body: collaborator
    });
  }
}
