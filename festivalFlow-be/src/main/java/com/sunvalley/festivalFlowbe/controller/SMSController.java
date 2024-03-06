package com.sunvalley.festivalFlowbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/festival-flow/sms")
public class SMSController {

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity boh() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody String phoneNumber) {
        log.info("phoneNumber: " + phoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
