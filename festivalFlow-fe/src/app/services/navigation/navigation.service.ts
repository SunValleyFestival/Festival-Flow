import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private router: Router) { }

  goToHome() {
    console.log("goToHome");
    this.router.navigate(['/user']);
  }

  logout() {
    this.router.navigate(['/user/login']);
  }

  goToLogin() {
    this.router.navigate(['/user/login']);
  }

  goToUserPage() {
    this.router.navigate(['/user/user']);
  }
}
