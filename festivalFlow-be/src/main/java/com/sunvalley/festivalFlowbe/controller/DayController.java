package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/")
public class DayController {

    private static final String ADMIN = "/admin/day/";
    private static final String DAY = "user/day/";

    private final DayService dayService;

    @Autowired
    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

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
    @DeleteMapping(ADMIN)
    public ResponseEntity<DayEntity> deleteById(@RequestBody DayEntity dayEntity) {
        dayService.deleteById(dayEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(ADMIN + "create")
    public ResponseEntity<DayEntity> create(@RequestBody DayEntity day) {
        DayEntity newDay = dayService.create(day);
        return new ResponseEntity<>(newDay, HttpStatus.CREATED);
    }

}