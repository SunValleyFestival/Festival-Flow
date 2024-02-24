import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Collaborator} from "../../interfaces/CollaboratorEntity";

const BASE_URL = environment.baseUrl + "/collaborator";
@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpClient) {
  }

  getCollaborators() {
    return this.http.get(BASE_URL + "/all");
  }

  saveCollaborator(collaborator: Collaborator) {

  }
}
