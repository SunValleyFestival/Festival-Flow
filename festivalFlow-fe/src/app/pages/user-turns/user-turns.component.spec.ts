import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTurnsComponent } from './user-turns.component';

describe('UserTurnsComponent', () => {
  let component: UserTurnsComponent;
  let fixture: ComponentFixture<UserTurnsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserTurnsComponent]
    });
    fixture = TestBed.createComponent(UserTurnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
