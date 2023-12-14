import {Component} from '@angular/core';
import {AdminTestService} from "../service/http/admin-test.service";
import {PortalTestService} from "../service/http/portal-test.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  adminResult: any;
  portalResult: any;

  constructor(private adminService: AdminTestService, private portalService: PortalTestService) {
  }


  testAdmin() {
    this.adminService.getTest().subscribe(result => {
      this.adminResult = result;
    });
  }

  testPortal() {
    this.portalService.getTest().subscribe(result => {
      this.portalResult = result;
    });

  }
}
