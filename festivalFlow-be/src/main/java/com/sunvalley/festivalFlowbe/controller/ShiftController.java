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
    private static final String SHIFT = "user/shift/";

    private final ShiftService shiftService;
    private final LocationService locationService;
    private final CollaboratorService collaboratorService;
    private final JWTTokenProviderService jwtTokenProviderService;


    @CrossOrigin
    @GetMapping(SHIFT + "{id}")
    public ResponseEntity<ShiftEntity> getById(@RequestHeader("Authorization") String token, @PathVariable int id) throws ParseException {
        ShiftEntity shift;
        if (isMinor(token)) {
            shift = shiftService.getByIdOnlyMinors(id);
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
            shifts = shiftService.findAllByLocationIdAndOnlyMinors(location);
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
    @GetMapping(SHIFT + "all")
    public ResponseEntity<List<ShiftEntity>> getAll() throws ParseException {
        List<ShiftEntity> shifts = shiftService.getAll();
        if (shifts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(shifts, HttpStatus.OK);
        }
    }

    private boolean isMinor(String token) throws ParseException {
        return collaboratorService.isMinor(jwtTokenProviderService.getUserIdFromToken(token));
    }

}
