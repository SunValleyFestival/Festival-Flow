package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.AssociationEntityId;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow")
public class AssociationController {

    private static final String ADMIN = "/admin/association/";
    private static final String ASSOCIATION = "/user/association/";

    private final AssociationService associationService;

    @Autowired
    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @CrossOrigin
    @GetMapping(ASSOCIATION)
    public ResponseEntity<List<AssociationEntity>> getAll() {
        List<AssociationEntity> associations = associationService.getAll();
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(ASSOCIATION + "create")
    public ResponseEntity<AssociationEntity> create(@RequestBody AssociationEntityId associationId) {
        var association = new AssociationEntity();
        association.setId(associationId);
        AssociationEntity newAssociation = associationService.save(association);
        return new ResponseEntity<>(newAssociation, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(ADMIN + "accept/{collaboratorId}")
    public ResponseEntity<AssociationEntity> update(@PathVariable int collaboratorId) {
        AssociationEntity association = associationService.getByCollaboratorId(collaboratorId);

        if (association == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (association.getStatus() == Status.ACCEPTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        }
        if (association.getStatus() == Status.REJECTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        }
        if (association.getStatus() == Status.PENDING) {
            association.setStatus(Status.ACCEPTED);
            return new ResponseEntity<>(association, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping(ADMIN + "reject/{collaboratorId}")
    public ResponseEntity<AssociationEntity> reject(@PathVariable int collaboratorId) {
        AssociationEntity association = associationService.getByCollaboratorId(collaboratorId);

        if (association == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (association.getStatus() == Status.ACCEPTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        }
        if (association.getStatus() == Status.REJECTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        }
        if (association.getStatus() == Status.PENDING) {
            association.setStatus(Status.REJECTED);
            return new ResponseEntity<>(association, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
