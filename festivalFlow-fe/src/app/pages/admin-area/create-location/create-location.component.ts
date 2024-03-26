import {Component, OnInit} from '@angular/core';
import {Location} from "../../../interfaces/LocationEntity";
import {Day} from "../../../interfaces/DayEntity";
import {LocationService} from "../../../services/http/location.service";
import {Router} from "@angular/router";
import {DayService} from "../../../services/http/day.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-location',
  templateUrl: './create-location.component.html',
  styleUrls: ['./create-location.component.css']
})
export class CreateLocationComponent implements OnInit {
  protected dataError: boolean = false;
  protected days: Day[] = [];
  protected image: File = {} as File;

  protected locationForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    day: ['', Validators.required],
    manager: [''],
    adultsOnly: [false],
    image: [null, Validators.required],
  });

  constructor(
    private locationService: LocationService,
    private router: Router,
    private dayService: DayService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.dayService.getAllDays().pipe().subscribe(days => {
      this.days = days;
    });
  }


  submitData() {
    this.locationService.createLocation(this.getLocationFromForm()).pipe().subscribe((location: Location) => {
      if (location.id != null) {
        this.locationService.uploadImage(this.image, location.id).pipe().subscribe(() => {
          this.router.navigate(['/admin']);
        });
      }
    });
  }

  getLocationFromForm(): Location {
    let name = this.locationForm.get('name')?.value;
    let description = this.locationForm.get('description')?.value;
    let id = this.locationForm.get('day')?.value;
    let manager = this.locationForm.get('manager')?.value;
    let adultsOnly = this.locationForm.get('adultsOnly')?.value;

    console.log(this.locationForm);

    if (name && description && id && manager && adultsOnly != undefined) {
      return {
        name: name,
        description: description,
        manager: manager,
        adultsOnly: adultsOnly,
        day: {
          id: Number(id)
        }
      };
    } else {
      return {} as Location;
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.image = file;
      console.log(file)
    }
  }

}
