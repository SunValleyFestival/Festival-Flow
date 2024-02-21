import {Component} from '@angular/core';
import {Router} from "@angular/router";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  items: string[] = ['Elemento 1', 'Elemento 2', 'Elemento 3'];

  constructor(private router: Router){}

  openDetail() {
    this.router.navigate(['location']);
  }
}
