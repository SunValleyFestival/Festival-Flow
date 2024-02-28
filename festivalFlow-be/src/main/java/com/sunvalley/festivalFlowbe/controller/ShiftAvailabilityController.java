package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import com.sunvalley.festivalFlowbe.service.ShiftAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival-flow/shift-availability")
public class ShiftAvailabilityController {

  private ShiftAvailabilityService shiftAvailabilityService;

  @Autowired
  public ShiftAvailabilityController(ShiftAvailabilityService shiftAvailabilityService) {
    this.shiftAvailabilityService = shiftAvailabilityService;
  }

  @GetMapping("/{shiftId}")
  public ResponseEntity<Integer> getShiftAvailability(@PathVariable int shiftId) {
    var shiftAvailability = shiftAvailabilityService.getByShiftId(shiftId);
    return new ResponseEntity<>(shiftAvailability.getAvailableSlots(), HttpStatus.OK);
  }

  @GetMapping("/location/{locationId}")
  public ResponseEntity<Integer> getAvailableSlotsByLocationId(@PathVariable int locationId) {
    var availableSlots = shiftAvailabilityService.getAvailableSlotsByLocationId(locationId);
    return new ResponseEntity<>(availableSlots, HttpStatus.OK);
  }

}
