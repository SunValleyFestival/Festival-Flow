package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {

  private final CollaboratorService collaboratorService;

  @Autowired
  public CollaboratorController(CollaboratorService collaboratorService) {
    this.collaboratorService = collaboratorService;
  }

  @CrossOrigin
  @GetMapping("/all")
  public ResponseEntity<List<CollaboratorEntity>> getAll() {
    var collaborators =  collaboratorService.getAll();
    return new ResponseEntity<>(collaborators, HttpStatus.OK);
  }
}
