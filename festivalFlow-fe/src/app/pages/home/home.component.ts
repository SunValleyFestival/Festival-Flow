import {Component} from '@angular/core';
import {AdminTestService} from "../../service/http/admin-test.service";
import {PortalTestService} from "../../service/http/portal-test.service";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  items: string[] = ['Elemento 1', 'Elemento 2', 'Elemento 3'];

}
