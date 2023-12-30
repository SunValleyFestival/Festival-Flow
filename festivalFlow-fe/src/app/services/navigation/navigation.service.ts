import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private router: Router, private auth: AuthService) { }

  authAndGoToHome() {
    this.auth.setLoggedIn(true);
    this.router.navigate(['/home']);
  }

  logout() {
    this.auth.setLoggedIn(false);
    this.router.navigate(['/login']);
  }
}
