import { Component } from '@angular/core';
import {FormControl} from '@angular/forms';
import {debounceTime} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected password: any;

  constructor(private router: Router) {
  }

  checkCredentials(password: string) {
    if (password === 'admin') {
      this.router.navigate(['/home']);
    } else {
      console.log('Login failed');
    }
  }

  onSubmit() {
    this.checkCredentials(this.password);
  }
}
