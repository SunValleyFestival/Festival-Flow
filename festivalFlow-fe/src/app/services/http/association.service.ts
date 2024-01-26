import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const BASE_URL = environment.baseUrl + "/association";

@Injectable({
  providedIn: 'root'
})
export class AssociationService {

  constructor(private http: HttpClient) {
  }

  getAssociations() {
    return this.http.get(BASE_URL) + "/all";
  }
}
