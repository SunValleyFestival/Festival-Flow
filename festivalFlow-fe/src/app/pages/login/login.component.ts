import { Component } from '@angular/core';
import {NavigationService} from "../../services/navigation/navigation.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected mail: any;

  constructor(private navigationService: NavigationService) {
  }

  checkCredentials(mail: string) {
    if(RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$').test(mail)) {
      this.mail = mail;
      this.navigationService.authAndGoToHome();
    }
  }

  onSubmit() {
    this.checkCredentials(this.mail);
  }
}
