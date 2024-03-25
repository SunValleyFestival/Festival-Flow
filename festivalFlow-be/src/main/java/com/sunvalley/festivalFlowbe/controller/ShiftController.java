package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class ShiftController {

    private static final String ADMIN = "admin/shift/";
    private static final String SHIFT = "user/shift/";

    private final ShiftService shiftService;
    private final LocationService locationService;
    private final CollaboratorService collaboratorService;
    private final JWTTokenProviderService jwtTokenProviderService;


    @CrossOrigin
    @GetMapping(SHIFT + "{id}")
    public ResponseEntity<ShiftEntity> getById(@RequestHeader("Authorization") String token, @PathVariable int id) throws ParseException {
        ShiftEntity shift;
        shift = shiftService.getById(id);
        return new ResponseEntity<>(shift, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(SHIFT + "location/{location}")
    public ResponseEntity<List<ShiftEntity>> getByLocationId(@RequestHeader("Authorization") String token, @PathVariable int location) throws ParseException {
        List<ShiftEntity> shifts;
            shifts = shiftService.getShiftsByLocationId(location);
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

}
