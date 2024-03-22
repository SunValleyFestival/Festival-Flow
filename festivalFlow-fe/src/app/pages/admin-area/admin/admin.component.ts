import {Component, OnInit} from '@angular/core';
import {Day} from "../../../interfaces/DayEntity";
import {Location, LocationClient} from "../../../interfaces/LocationEntity";
import {ActivatedRoute, Router} from "@angular/router";
import {DayService} from "../../../services/http/day.service";
import {LocationService} from "../../../services/http/location.service";
import {ShiftAvailabilityService} from "../../../services/http/shift-availability.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ExportService} from "../../../services/http/export.service";
import {EmailRequest} from "../../../interfaces/EmailRequestEntity";

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
  protected buttonDisabled: boolean = false;


  protected exportForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
  });

  protected dayForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required]
  });

  constructor(
    private router: Router,
    private dayService: DayService,
    private locationService: LocationService,
    private shiftAvailabilityService: ShiftAvailabilityService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private exportService: ExportService,

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

    this.dayService.saveDay(this.getDayFromForm()).pipe().subscribe();
    this.dayForm.reset();

    window.location.reload();
  }

  getDayFromForm(): Day {
    return {
      name: this.dayForm.get('name')?.value,
      description: this.dayForm.get('description')?.value
    }
  }

  filterLocation() {
    this.filteredLocations = this.locations.filter(location => {
      return location.name.toLowerCase().includes(this.nameToFilter.toLowerCase());
    });
  }

  submitEmailToExport() {

    this.exportService.exportByMail(this.getEmailRequestFromForm()).pipe().subscribe();
    this.buttonDisabled = true;
    //window.location.reload();
  }

  getEmailRequestFromForm(): EmailRequest {
    return {
      to: this.exportForm.get('email')?.value,
      description: '',
      subject: ''
    }
  }
}
