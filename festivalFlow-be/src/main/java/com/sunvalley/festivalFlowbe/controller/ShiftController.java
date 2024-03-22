package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/")
public class ShiftController {

    private static final String ADMIN = "admin/shift/";
    private static final String SHIFT = "user/shift/";

    private final ShiftService shiftService;
    private final LocationService locationService;
    private final CollaboratorService collaboratorService;
    private final JWTTokenProviderService jwtTokenProviderService;

    public ShiftController(ShiftService shiftService, LocationService locationService, CollaboratorService collaboratorService, JWTTokenProviderService jwtTokenProviderService) {
        this.shiftService = shiftService;
        this.locationService = locationService;
        this.collaboratorService = collaboratorService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }


    @CrossOrigin
    @GetMapping(SHIFT + "{id}")
    public ResponseEntity<ShiftEntity> getById(@RequestHeader("Authorization") String token, @PathVariable int id) throws ParseException {
        ShiftEntity shift;
        if (isMinor(token)) {
            shift = shiftService.getByIdOnlyAdult(id);
            if (shift == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(shift, HttpStatus.OK);
            }
        }

        shift = shiftService.getById(id);
        return new ResponseEntity<>(shift, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(SHIFT + "location/{location}")
    public ResponseEntity<List<ShiftEntity>> getByLocationId(@RequestHeader("Authorization") String token, @PathVariable int location) throws ParseException {
        List<ShiftEntity> shifts;
        if (isMinor(token)) {
            shifts = shiftService.finAllByLocationIdAndOnlyAdult(location, true);
        } else {
            shifts = shiftService.getShiftsByLocationId(location);
        }
        if (shifts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(shifts, HttpStatus.OK);
        }

    }

    @CrossOrigin
    @PostMapping(ADMIN + "create")
    public ResponseEntity<ShiftEntity> create(@RequestBody ShiftEntity shift) {
        shift.setId(null);
        shift.setLocation(locationService.getById(shift.getLocation().getId(), false));
        if (shift.getLocation() == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        ShiftEntity newShift = shiftService.create(shift);
        return new ResponseEntity<>(newShift, HttpStatus.CREATED);
    }

    @CrossOrigin
    @DeleteMapping(ADMIN)
    public ResponseEntity<ShiftEntity> deleteById(@RequestBody ShiftEntity shiftEntity) {
        shiftService.deleteById(shiftEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isMinor(String token) throws ParseException {
        return collaboratorService.isMinor(jwtTokenProviderService.getUserIdFromToken(token));
    }

}
