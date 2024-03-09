import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private router: Router) { }

  goToHome() {
    this.router.navigate(['/home']);
  }

  logout() {
    this.router.navigate(['/login']);
  }
}
