package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<List<LocationEntity>> getAll() {
        List<LocationEntity> Locations = locationService.getAll();
        return new ResponseEntity<>(Locations, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/day/{day}")
    public ResponseEntity<List<LocationEntity>> getByDayId(@PathVariable int day) {
        List<LocationEntity> locations = locationService.getLocationsByDayId(day);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<LocationEntity> create(@RequestBody LocationEntity location) {
        LocationEntity newLocation = locationService.create(location);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }


}
