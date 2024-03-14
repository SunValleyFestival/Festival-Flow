import {Component, OnInit} from '@angular/core';
import {Location} from "../../../interfaces/LocationEntity";
import {Day} from "../../../interfaces/DayEntity";
import {timer} from "rxjs";
import {LocationService} from "../../../services/http/location.service";
import {Router} from "@angular/router";
import {DayService} from "../../../services/http/day.service";

@Component({
  selector: 'app-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {
  protected dataError: boolean = false;
  protected days: Day[] = [];
  protected formData: Location = {
    name: '',
    description: '',
    day: {
      id: 0,
    } as Day,
  }

  constructor(
    private locationService: LocationService,
    private router: Router,
    private dayService: DayService,
  ) {
  }

  ngOnInit(): void {
    this.dayService.getAllDays().pipe().subscribe(days => {
      this.days = days;
    });
  }


  submitData() {
    if (this.checkData()) {
      return;
    }

    this.locationService.createLocation(this.formData).pipe().subscribe();
    this.router.navigate(['/admin']);
  }

  checkData(): boolean {
    let error = this.formData.name === '' || this.formData.description === '' || this.formData.day.id === -1;

    console.log(this.formData)

    this.dataError = error;

    if (error) {
      timer(5000).subscribe(() => {
        this.dataError = false;
      });
    }

    return error;
  }
}
