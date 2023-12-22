import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected phone: any;

  constructor() {
  }

  checkCredentials(phone: string) {
    if (phone === '0041795890065') {
      console.log('Login successful');
    } else {
      console.log('Login failed');
    }
  }

  onSubmit() {
    this.phone = '0041' + this.phone;
    this.checkCredentials(this.phone);
  }
}
