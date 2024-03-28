package com.sunvalley.festivalFlowbe.controller.admin;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import com.sunvalley.festivalFlowbe.service.ShiftAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class ShiftAvailabilityAdminController {

    private static final String ADMIN = "admin/shift-availability/";

    private final ShiftAvailabilityService shiftAvailabilityService;


    @GetMapping(ADMIN + "location/{locationId}")
    public ResponseEntity<ShiftAvailabilityView> getAvailableSlotsByLocationId(@PathVariable int locationId) {
        var shiftAvailability = shiftAvailabilityService.getAvailableSlotsByLocationId(locationId);
        return new ResponseEntity<>(shiftAvailability, HttpStatus.OK);
    }

}
