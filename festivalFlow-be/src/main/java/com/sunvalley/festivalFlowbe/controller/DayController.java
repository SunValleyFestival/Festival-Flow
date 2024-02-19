package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/day")
public class DayController {

  private final DayService dayService;

  @Autowired
  public DayController(DayService dayService) {
    this.dayService = dayService;
  }

  @CrossOrigin
  @GetMapping("/")
  public ResponseEntity<List<DayEntity>> getAll(){
    List<DayEntity> days = dayService.getAll();
    return new ResponseEntity<>(days, HttpStatus.OK);
  }

}