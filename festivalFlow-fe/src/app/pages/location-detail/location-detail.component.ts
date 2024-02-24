import {Component, OnInit} from '@angular/core';
import {ShiftService} from "../../services/http/shift.service";
import {ActivatedRoute} from "@angular/router";
import {Shift} from "../../interfaces/ShiftEntity";

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent implements OnInit {
  protected selectedDay: number | undefined;
  protected selectedLocation: number | undefined;
  protected signedIn: boolean = false;
  protected shifts: Shift[] | undefined;

  constructor(private shiftService: ShiftService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.selectedLocation = params['location'];
      this.selectedDay = params['day'];
    });

    this.shiftService.getShiftByLocationIdAndDay(this.selectedLocation, this.selectedDay).pipe().subscribe((shifts: any) => {
      this.shifts = shifts;
    })
  }

}
