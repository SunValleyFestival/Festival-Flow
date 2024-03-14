package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntityAdmin;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/festival-flow/")
public class ShiftController {

    private static final String ADMIN = "admin/shift/";
    private static final String SHIFT = "user/shift/";

    private final ShiftService shiftService;

    private final LocationService locationService;

    public ShiftController(ShiftService shiftService, LocationService locationService) {
        this.shiftService = shiftService;
        this.locationService = locationService;
    }

    @CrossOrigin
    @GetMapping(SHIFT)
    public ResponseEntity<List<ShiftEntity>> getAll() {
        List<ShiftEntity> shifts = shiftService.getAll();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(SHIFT + "{id}")
    public ResponseEntity<ShiftEntity> getById(@PathVariable int id) {
        ShiftEntity shift = shiftService.getById(id);
        return new ResponseEntity<>(shift, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(SHIFT + "location/{location}")
    public ResponseEntity<List<ShiftEntity>> getByLocationId(@PathVariable int location) {
        List<ShiftEntity> shifts = shiftService.getShiftsByLocationId(location);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(ADMIN + "location/{location}")
    public ResponseEntity<List<ShiftEntityAdmin>> getAdminShiftByLocationId(@PathVariable int location) {
        List<ShiftEntity> shifts = shiftService.getShiftsByLocationId(location);
        List<ShiftEntityAdmin> shiftEntityAdminList = shiftService.getShiftAdmin(shifts);
        return new ResponseEntity<>(shiftEntityAdminList, HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping(ADMIN + "create")
    public ResponseEntity<ShiftEntity> create(@RequestBody ShiftEntity shift) {
        shift.setId(null);
        shift.setLocation(locationService.getById(shift.getLocation().getId()));
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
