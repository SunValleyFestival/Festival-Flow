package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/festival-flow/association")
public class AssociationController {

  private final AssociationService associationService;

  @Autowired
  public AssociationController(AssociationService associationService) {
    this.associationService = associationService;
  }

  @CrossOrigin
  @GetMapping("/")
  public ResponseEntity<List<AssociationEntity>> getAll(){
    List<AssociationEntity> associations = associationService.getAll();
    return new ResponseEntity<>(associations, HttpStatus.OK);
  }
}
