package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival-flow/shift")
public class ShiftController {

  private final ShiftService shiftService;

  public ShiftController(ShiftService shiftService) {
    this.shiftService = shiftService;
  }

  @CrossOrigin
  @GetMapping("/all")
  public ResponseEntity<List<ShiftEntity>> getAll() {
    var shifts = shiftService.getAll();
    return new ResponseEntity<>(shifts, HttpStatus.OK);
  }
}
