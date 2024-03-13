package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.service.DayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/")
@RequiredArgsConstructor
public class DayController {

    private static final String ADMIN = "admin/day/";
    private static final String DAY = "user/day/";

    private final DayService dayService;

    @CrossOrigin
    @GetMapping(DAY)
    public ResponseEntity<List<DayEntity>> getAll() {
        List<DayEntity> days = dayService.getAll();
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(DAY + "{id}")
    public ResponseEntity<DayEntity> getById(@PathVariable int id) {
        DayEntity day = dayService.getById(id);
        return new ResponseEntity<>(day, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(ADMIN + "{id}")
    public ResponseEntity<DayEntity> deleteById(@PathVariable int id) {
        dayService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(ADMIN + "create")
    public ResponseEntity<DayEntity> create(@RequestBody DayEntity day) {
        day.setId(null);
        DayEntity newDay = dayService.create(day);
        return new ResponseEntity<>(newDay, HttpStatus.CREATED);
    }

}