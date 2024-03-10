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
@RequestMapping("/festival-flow/")
public class LocationController {

    private static final String ADMIN = "/admin/location/";
    private static final String LOCATION = "user/location/";


    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @CrossOrigin
    @GetMapping(LOCATION)
    public ResponseEntity<List<LocationEntity>> getAll() {
        List<LocationEntity> Locations = locationService.getAll();
        return new ResponseEntity<>(Locations, HttpStatus.OK);
    }

    @GetMapping(LOCATION + "{id}")
    public ResponseEntity<LocationEntity> getById(@PathVariable int id) {
        LocationEntity location = locationService.getById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(LOCATION + "day/{day}")
    public ResponseEntity<List<LocationEntity>> getByDayId(@PathVariable int day) {
        List<LocationEntity> locations = locationService.getLocationsByDayId(day);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(ADMIN + "create")
    public ResponseEntity<LocationEntity> create(@RequestBody LocationEntity location) {
        location.setId(null);
        LocationEntity newLocation = locationService.create(location);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @CrossOrigin
    @DeleteMapping(ADMIN)
    public ResponseEntity<LocationEntity> deleteById(@RequestBody LocationEntity locationEntity) {
        locationService.deleteById(locationEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
