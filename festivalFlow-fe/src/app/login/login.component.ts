import { Component } from '@angular/core';
import {NavigationService} from "../services/navigation/navigation.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected phone: any;

  constructor(private navigationService: NavigationService) {
  }

  checkCredentials(phone: string) {
    if (phone === '0041795890065') {
      this.navigationService.authAndGoToHome();
    } else {
      console.log('Login failed');
    }
  }

  onSubmit() {
    this.phone = '0041' + this.phone;
    this.checkCredentials(this.phone);
  }
}
