package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow")
public class AssociationController {

    private static final String ADMIN = "/admin/association/";
    private static final String ASSOCIATION = "/user/association/";

    private final AssociationService associationService;
    private final ShiftService shiftService;
    private final CollaboratorService collaboratorService;

    private final JWTTokenProviderService jwtTokenProviderService;

    @Autowired
    public AssociationController(AssociationService associationService, ShiftService shiftService, CollaboratorService collaboratorService, JWTTokenProviderService jwtTokenProviderService) {
        this.associationService = associationService;
        this.shiftService = shiftService;
        this.collaboratorService = collaboratorService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    @CrossOrigin
    @GetMapping(ASSOCIATION)
    public ResponseEntity<List<AssociationEntity>> getAll() {
        List<AssociationEntity> associations = associationService.getAll();
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(ASSOCIATION + "collaboratorId/{id}")
    public ResponseEntity<List<AssociationEntity>> getByTypeAndId(int id) {
        List<AssociationEntity> locationEntities;
        locationEntities = associationService.getByCollaboratorId(id);
        return new ResponseEntity<>(locationEntities, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(ASSOCIATION + "create}")
    public ResponseEntity<AssociationEntity> create(@RequestBody AssociationEntity associationEntity, @RequestHeader("Authorization") String token) throws ParseException {
        if (!jwtTokenProviderService.getUserIdFromToken(token).equals(associationEntity.getId().getCollaboratorId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            if (shiftService.getById(associationEntity.getId().getShiftId()).isAdultsOnly()) {
                Date dataNascita = collaboratorService.getById(associationEntity.getId().getCollaboratorId()).getAge();
                long differenzaMillisecondi = new Date().getTime() - dataNascita.getTime();
                long anni = differenzaMillisecondi / (1000L * 60 * 60 * 24 * 365);
                if (anni < 18) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            associationEntity.setStatus(Status.PENDING);
            associationService.save(associationEntity);
            return new ResponseEntity<>(associationEntity, HttpStatus.OK);
        }


    }

    @CrossOrigin
    @PutMapping(ADMIN + "accept/")
    public ResponseEntity<AssociationEntity> accept(@RequestBody AssociationEntity associationEntity) {
        AssociationEntity association = associationService.getByCollaboratorIdAndShiftId(associationEntity.getId().getCollaboratorId(), associationEntity.getId().getShiftId());

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
