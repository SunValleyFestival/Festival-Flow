package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/festival-flow/")
public class LocationController {

    private static final String ADMIN = "admin/location/";
    private static final String LOCATION = "user/location/";


    private final LocationService locationService;
    private final CollaboratorService collaboratorService;
    private final JWTTokenProviderService jwtTokenProviderService;

    @Autowired
    public LocationController(LocationService locationService, CollaboratorService collaboratorService, JWTTokenProviderService jwtTokenProviderService) {
        this.locationService = locationService;
        this.collaboratorService = collaboratorService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }


    @GetMapping(LOCATION + "{id}")
    public ResponseEntity<LocationEntity> getById(@RequestHeader("Authorization") String token, @PathVariable int id) throws ParseException {
        LocationEntity location = locationService.getById(id, isMinor(token));
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping(LOCATION + "day/{day}")
    public ResponseEntity<List<LocationEntity>> getByDayId(@RequestHeader("Authorization") String token, @PathVariable int day) throws ParseException {
        List<LocationEntity> locations = locationService.getLocationsByDayId(day, isMinor(token));
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping(ADMIN + "create")
    public ResponseEntity<LocationEntity> create(@RequestBody LocationEntity location) {
        location.setId(null);
        LocationEntity newLocation = locationService.create(location);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @DeleteMapping(ADMIN)
    public ResponseEntity<LocationEntity> deleteById(@RequestBody LocationEntity locationEntity) {
        locationService.deleteById(locationEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isMinor(String token) throws ParseException {
        return collaboratorService.isMinor(jwtTokenProviderService.getUserIdFromToken(token));
    }

}
