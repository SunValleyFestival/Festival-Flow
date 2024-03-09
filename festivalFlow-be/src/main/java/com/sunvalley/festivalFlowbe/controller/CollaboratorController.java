package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
@RequestMapping("/festival-flow")
public class CollaboratorController {

    private static final String ADMIN = "/admin/collaborator/";
    private static final String COLLABORATOR = "/collaborator/";

    private final CollaboratorService collaboratorService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @CrossOrigin
    @GetMapping(COLLABORATOR)
    public ResponseEntity<List<CollaboratorEntity>> getAll() {
        List<CollaboratorEntity> collaborators = collaboratorService.getAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }


    @DeleteMapping (ADMIN)
    public ResponseEntity<CollaboratorEntity> deleteById(@RequestBody CollaboratorEntity collaboratorEntity) {
        collaboratorService.deleteById(collaboratorEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
