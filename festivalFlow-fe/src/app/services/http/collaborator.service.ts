import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {HttpAuthClient} from "./token/http-auth-client";

const BASE_URL = environment.baseUrl + "/collaborator";
@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpAuthClient) {
  }

  getCollaborators() {
    return this.http.get(BASE_URL + "/all");
  }

  saveCollaborator(collaborator: Collaborator) {

  }
}
