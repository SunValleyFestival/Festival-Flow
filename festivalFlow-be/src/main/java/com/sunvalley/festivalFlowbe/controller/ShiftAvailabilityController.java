package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/festival-flow/")
public class ShiftAvailabilityController {

    private static final String SHIFT_AVAILABILITY = "user/shift-availability/";

    private final AssociationService associationService;

    @Autowired
    public ShiftAvailabilityController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @CrossOrigin
    @GetMapping(SHIFT_AVAILABILITY + "{shiftId}")
    public ResponseEntity<Integer> getShift(@PathVariable int shiftId) {
        Integer shiftAvailability = associationService.countByShiftIdAndStatusNotReject(shiftId);
        return new ResponseEntity<>(shiftAvailability, HttpStatus.OK);
    }

}
