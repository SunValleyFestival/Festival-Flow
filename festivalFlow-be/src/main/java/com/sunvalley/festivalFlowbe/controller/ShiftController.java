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
@RequestMapping("/festival-flow/shift")
public class ShiftController {

  private final ShiftService shiftService;

  public ShiftController(ShiftService shiftService) {
    this.shiftService = shiftService;
  }

  @CrossOrigin
  @GetMapping("/")
  public ResponseEntity<List<ShiftEntity>> getAll() {
    List<ShiftEntity> shifts = shiftService.getAll();
    return new ResponseEntity<>(shifts, HttpStatus.OK);
  }

  @CrossOrigin
  @GetMapping("/location/{location}")
  public ResponseEntity<List<ShiftEntity>> getByLocationId(@PathVariable int location) {
    List<ShiftEntity> shifts = shiftService.getShiftsByLocationId(location);
    return new ResponseEntity<>(shifts, HttpStatus.OK);
  }


}
