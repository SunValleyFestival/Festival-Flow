package com.sunvalley.festivalFlowbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival-flow/admin")
public class AdminController {

  @CrossOrigin
  @GetMapping("")
  public ResponseEntity<HttpStatus> get(){
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
