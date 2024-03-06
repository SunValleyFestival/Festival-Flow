import {Component, OnInit} from '@angular/core';
import {Day} from "../../../interfaces/DayEntity";
import {Location} from "../../../interfaces/LocationEntity";
import {ShiftAvailability} from "../../../interfaces/ShiftAvailabilityView";
import {Router} from "@angular/router";
import {DayService} from "../../../services/http/day.service";
import {LocationService} from "../../../services/http/location.service";
import {ShiftAvailabilityService} from "../../../services/http/shift-availability.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  protected days: Day[] = [{id: 0, name: "Venerdì"}];
  protected locations: Location[] = [{id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}, {id: 0, name: "Location 1", description: '', day: {id: 0, name: "Venerdì"}}];
  protected currentDay: number = 0;
  protected locationAvailability: ShiftAvailability[] = [];
  protected formData: Day = {
    name: '',
    description: ''
  };

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
    this.router.navigate(['edit']);
  }


  getLocationsByDayId(dayId: string): void {
    this.currentDay = Number(dayId);
    this.locationService.getLocationsByDayId(Number(dayId)).pipe().subscribe((locations: any) => {
      this.locations = locations;

      for (let location of this.locations) {
        this.shiftAvailabilityService.getAvailableSlotsByLocationId(location.id).pipe().subscribe((shiftAvailability: any) => {
          this.locationAvailability.push(shiftAvailability);
        });
      }
    });
  }

  goToUserPage() {
    this.router.navigate(['user']);
  }

  goToCreateLocationPage() {
    this.router.navigate(['create']);
  }

  deleteLocation(id: number | undefined) {

  }

  submitData() {

  }
}
