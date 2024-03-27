package com.sunvalley.festivalFlowbe.controller.admin;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.service.DayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class DayAdminController {

    private static final String ADMIN = "admin/day/";

    private final DayService dayService;

    @GetMapping(ADMIN)
    public ResponseEntity<List<DayEntity>> getAll() {
        List<DayEntity> days = dayService.getAll();
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @DeleteMapping(ADMIN + "{id}")
    public ResponseEntity<DayEntity> deleteById(@PathVariable int id) {
        dayService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ADMIN + "create")
    public ResponseEntity<DayEntity> create(@RequestBody DayEntity day) {
        day.setId(null);
        DayEntity newDay = dayService.create(day);
        return new ResponseEntity<>(newDay, HttpStatus.CREATED);
    }

}