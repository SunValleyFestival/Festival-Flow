import {Component, OnInit} from '@angular/core';
import {Day} from "../../../interfaces/DayEntity";
import {Location, LocationClient} from "../../../interfaces/LocationEntity";
import {ActivatedRoute, Router} from "@angular/router";
import {DayService} from "../../../services/http/day.service";
import {LocationService} from "../../../services/http/location.service";
import {ShiftAvailabilityService} from "../../../services/http/shift-availability.service";
import {timer} from "rxjs";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  protected days: Day[] = [];
  protected locations: LocationClient[] = [];
  protected filteredLocations: LocationClient[] = [];
  protected currentDayId: number = 0;
  protected dataError: boolean = false;
  protected nameToFilter: string = '';

  protected formData: Day = {
    name: '',
    description: ''
  };

  constructor(
    private router: Router,
    private dayService: DayService,
    private locationService: LocationService,
    private shiftAvailabilityService: ShiftAvailabilityService,
    private route: ActivatedRoute,
  ) {
  }


  ngOnInit() {
    this.dayService.getAllDays().pipe().subscribe((days: Day[]) => {
      this.days = days;
      this.route.params.subscribe(params => {
        if (params['day']) {
          this.getLocationsByDayId(params['day']);
        } else {
          if (this.days.length > 0) {
            this.getLocationsByDayId(String(this.days[0].id));
          }
        }
      });
    });
  }


  openDetail(location: Location | undefined) {
    this.router.navigate(['admin/location/' + location?.name + "/" + location?.id]);
  }

  changeDayAdmin(dayId: string) {
    this.router.navigate(['admin/' + dayId]);
  }


  getLocationsByDayId(dayId: string): void {
    this.currentDayId = Number(dayId);

    this.locationService.getLocationsByDayId(Number(dayId)).pipe().subscribe((locations: any) => {
      this.locations = locations;
      this.filteredLocations = locations;

      for (let location of locations) {
        this.shiftAvailabilityService.getAvailableSlotsByLocationId(location.id).pipe().subscribe((shiftAvailability: any) => {
          location.shiftAvailability = shiftAvailability.availableSlots
        });
      }
    });
  }

  goToUserPage() {
    this.router.navigate(['admin/user']);
  }

  goToCreateLocationPage() {
    this.router.navigate(['admin/create']);
  }

  deleteLocation(location: Location | undefined) {
      if(location !== undefined) {
        this.locationService.deleteLocation(location).pipe().subscribe();
        window.location.reload();
      }
  }

  submitData() {
    if (this.checkData()) return;

    this.dayService.saveDay(this.formData).pipe().subscribe();
    this.resetFormData();

    window.location.reload();
  }

  checkData(): boolean {
    let error = this.formData.name === '' || this.formData.description === '';
    this.dataError = error;
    if (error) {
      timer(5000).subscribe(() => {
        this.dataError = false;
      });
    }

    return error;
  }

  resetFormData() {
    this.formData = {
      name: '',
      description: ''
    };
  }

  filterLocation() {
    console.log(this.nameToFilter);
    this.filteredLocations = this.locations.filter(location => {
      return location.name.toLowerCase().includes(this.nameToFilter.toLowerCase());
    });
  }
}
