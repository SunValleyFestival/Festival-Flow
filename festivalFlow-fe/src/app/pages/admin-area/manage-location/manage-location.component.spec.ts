import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageLocationComponent } from './manage-location.component';

describe('ManageLocationComponent', () => {
  let component: ManageLocationComponent;
  let fixture: ComponentFixture<ManageLocationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageLocationComponent]
    });
    fixture = TestBed.createComponent(ManageLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
