import {Injectable} from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';

const BASE_URL = environment.baseUrl + "/association";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(private http: HttpAuthClient) {
  }

  getAssociations() {
    return this.http.get(BASE_URL) + "/all";
  }

  saveAssociation(collaboratorId: number, shiftId: number) {
    return this.http.post(BASE_URL + "/save", {collaboratorId, shiftId});

  }
}
