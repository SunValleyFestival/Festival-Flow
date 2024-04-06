import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Day} from "../../interfaces/DayEntity";
import {DayService} from "../../services/http/user/day.service";
import {LocationService} from "../../services/http/user/location.service";
import {LocationClient} from "../../interfaces/LocationEntity";
import {ShiftAvailabilityService} from "../../services/http/user/shift-availability.service";
import {SanitizerService} from "../../services/utility/sanitizer.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {CollaboratorService} from "../../services/http/user/collaborator.service";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  protected days: Day[] = [];
  protected locations: LocationClient[] = [];
  protected filteredLocations: LocationClient[] = [];
  protected currentDayId: number = 0;
  protected nameToFilter: string = '';
  protected activeCollaborator: Collaborator = {} as Collaborator;

  constructor(
    private router: Router,
    private dayService: DayService,
    private locationService: LocationService,
    private shiftAvailabilityService: ShiftAvailabilityService,
    private route: ActivatedRoute,
    private sanitizerService: SanitizerService,
    private collaboratorService: CollaboratorService
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

    this.collaboratorService.getCollaboratorFromToken().pipe().subscribe((collaborator: Collaborator) => {
      this.activeCollaborator = collaborator;

      console.log(this.activeCollaborator)

      if (this.activeCollaborator.firstName == undefined || this.activeCollaborator.firstName == '') {
        const dialog = document.getElementById('welcomeBanner') as HTMLDialogElement;
        if (dialog) {
          dialog.showModal();
        }
      }
    });
  }

  changeDay(value: string) {
    this.router.navigate(['user/' + value]);
  }


  openDetail(locationId: number | undefined) {
    this.router.navigate(['user/location/' + locationId]);
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

  filterLocation() {
    this.filteredLocations = this.locations.filter(location => {
      return location.name.toLowerCase().includes(this.nameToFilter.toLowerCase());
    });
  }

  sanitizeImageUrl(imageUrl: any | undefined) {
    if (imageUrl != undefined) {
      return this.sanitizerService.sanitizeImageUrl(imageUrl);
    }
    return '';
  }
}
