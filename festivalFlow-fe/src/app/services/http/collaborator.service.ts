import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const BASE_URL = environment.apiUrl + "/collaborator";
@Injectable({
  providedIn: 'root'
})
export class CollaboratorService {

  constructor(private http: HttpClient) {
  }

  getCollaborators() {
    return this.http.get(BASE_URL + "/all");
  }

}
