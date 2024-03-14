package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import com.sunvalley.festivalFlowbe.service.ShiftAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival-flow/")
public class ShiftAvailabilityController {

    private static final String ADMIN = "admin/shift-availability/";
    private static final String SHIFT_AVAILABILITY = "user/shift-availability/";

    private final ShiftAvailabilityService shiftAvailabilityService;

    @Autowired
    public ShiftAvailabilityController(ShiftAvailabilityService shiftAvailabilityService) {
        this.shiftAvailabilityService = shiftAvailabilityService;
    }

    @CrossOrigin
    @GetMapping(SHIFT_AVAILABILITY + "{shiftId}")
    public ResponseEntity<ShiftAvailabilityView> getShiftAvailability(@PathVariable int shiftId) {
        var shiftAvailability = shiftAvailabilityService.getByShiftId(shiftId);
        return new ResponseEntity<>(shiftAvailability, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(SHIFT_AVAILABILITY + "location/{locationId}")
    public ResponseEntity<ShiftAvailabilityView> getAvailableSlotsByLocationId(@PathVariable int locationId) {
        var shiftAvailability = shiftAvailabilityService.getAvailableSlotsByLocationId(locationId);
        return new ResponseEntity<>(shiftAvailability, HttpStatus.OK);
    }

}
