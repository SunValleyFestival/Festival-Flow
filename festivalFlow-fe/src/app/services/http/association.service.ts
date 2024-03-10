import {Injectable} from '@angular/core';
import {HttpAuthClient} from "./token/http-auth-client";
import {environment} from '../../../environments/environment';
import {Association} from "../../interfaces/AssociationEntity";

const BASE_URL = environment.userBaseUrl + "/association";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(private http: HttpAuthClient) {
  }

  getAssociations() {
    return this.http.get(BASE_URL) + "/all";
  }

  saveAssociation(association: Association) {
    return this.http.post(BASE_URL + "/save", association);
  }
}
