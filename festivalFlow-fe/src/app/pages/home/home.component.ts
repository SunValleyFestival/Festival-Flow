import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Day} from "../../interfaces/DayEntity";
import {DayService} from "../../services/http/day.service";
import {Observable} from "rxjs";
import {LocationService} from "../../services/http/location.service";
import {Location} from "../../interfaces/LocationEntity";
import {ShiftAvailabilityService} from "../../services/http/shift-availability.service";
import {ShiftAvailability} from "../../interfaces/ShiftAvailabilityView";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  protected days: Day[] = [];
  protected locations: Location[] = [];
  protected currentDay: number = 0;
  protected locationAvailability: ShiftAvailability[] = [];

  constructor(
    private router: Router,
    private dayService: DayService,
    private locationService: LocationService,
    private shiftAvailabilityService: ShiftAvailabilityService,
  ) {
  }


  ngOnInit() {
    this.dayService.getAllDays().pipe().subscribe((days: Day[]) => {
      this.days = days;
      if (days.length > 0) {
        if (days[0].id) {
          this.currentDay = days[0].id;
          this.getLocationsByDayId(String(this.currentDay));
        }
      }
    });
  }


  openDetail(locationId: number | undefined) {
    this.router.navigate(['location/' + locationId + '/' + this.currentDay]);
  }


  getLocationsByDayId(dayId: string): void {
    this.currentDay = Number(dayId);
    this.locationService.getLocationsByDayId(Number(dayId)).pipe().subscribe((locations: any) => {
      this.locations = locations;

      for (let location of this.locations){
        this.shiftAvailabilityService.getShiftAvailability(location.id).pipe().subscribe((shiftAvailability: any) => {
          this.locationAvailability.push(shiftAvailability);
        });
      }
    });
  }

}
