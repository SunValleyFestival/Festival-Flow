package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow")
public class ShiftController {

    private static final String ADMIN = "/admin/shift/";
    private static final String SHIFT = "user/shift/";

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
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
    @PostMapping(ADMIN + "create")
    public ResponseEntity<ShiftEntity> create(@RequestBody ShiftEntity shift) {
        shift.setId(null);
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
