package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.service.AssociationService;
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
    private static final String ASSOCIATION = "user/association/";

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
    @PutMapping(ADMIN + "update")
    public ResponseEntity<AssociationEntity> update(@RequestBody AssociationEntity association) {
        AssociationEntity association = associationService.getByUserId(association.get);

        return new ResponseEntity<>(association, HttpStatus.OK);

        AssociationEntity newAssociation = associationService.update(association);
        return new ResponseEntity<>(newAssociation, HttpStatus.OK);
    }
}
