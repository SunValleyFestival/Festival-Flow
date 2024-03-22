package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationAdmin;
import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.service.*;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/")
public class AssociationController {

    private static final String ADMIN = "admin/association/";
    private static final String ASSOCIATION = "user/association/";

    private final AssociationService associationService;
    private final ShiftService shiftService;
    private final CollaboratorService collaboratorService;
    private final ShiftAvailabilityService shiftAvailabilityService;
    private final EmailService emailService;
    private final JWTTokenProviderService jwtTokenProviderService;

    @Autowired
    public AssociationController(AssociationService associationService, ShiftService shiftService, CollaboratorService collaboratorService, ShiftAvailabilityService shiftAvailabilityService, LocationService locationService, EmailService emailService, JWTTokenProviderService jwtTokenProviderService) {
        this.associationService = associationService;
        this.shiftService = shiftService;
        this.collaboratorService = collaboratorService;
        this.shiftAvailabilityService = shiftAvailabilityService;
        this.emailService = emailService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    @CrossOrigin
    @GetMapping(ASSOCIATION + "collaborator-id/{id}")
    public ResponseEntity<List<AssociationEntity>> getByTypeAndId(int id, @RequestHeader("Authorization") String token) throws ParseException {
        if (!jwtTokenProviderService.getUserIdFromToken(token).equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            List<AssociationEntity> locationEntities;
            locationEntities = associationService.getByCollaboratorId(id);
            return new ResponseEntity<>(locationEntities, HttpStatus.OK);
        }
    }


    @CrossOrigin
    @GetMapping(ADMIN + "shift/{shiftId}")
    public ResponseEntity<List<AssociationAdmin>> getCollaboratorsByShiftId(@PathVariable int shiftId) {
        List<AssociationAdmin> associations = associationService.getAssociationAdminByShiftId(shiftId);
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping(ASSOCIATION + "create")
    public ResponseEntity<AssociationEntity> create(@RequestBody AssociationEntity associationEntity, @RequestHeader("Authorization") String token) throws ParseException {
        if (!jwtTokenProviderService.getUserIdFromToken(token).equals(associationEntity.getId().getCollaboratorId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            if (shiftAvailabilityService.getByShiftId(associationEntity.getId().getShiftId()).getAvailableSlots() <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                if (shiftService.getById(associationEntity.getId().getShiftId()).getLocation().isAdultsOnly()) {
                    if (collaboratorService.isMinor(associationEntity.getId().getCollaboratorId())) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
                associationEntity.setStatus(Status.ACCEPTED);
                associationService.save(associationEntity);
                emailService.sendNotificationViaEmail(associationEntity.getId().getCollaboratorId(), Status.PENDING, associationEntity.getId().getShiftId());
                return new ResponseEntity<>(associationEntity, HttpStatus.OK);
            }
        }
    }

    @CrossOrigin
    @PutMapping(ADMIN + "accept")
    public ResponseEntity<AssociationEntity> accept(@RequestBody AssociationEntity associationEntity) {
        AssociationEntity association = associationService.getByCollaboratorIdAndShiftId(associationEntity.getId().getCollaboratorId(), associationEntity.getId().getShiftId());

        if (association == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (association.getStatus() == Status.ACCEPTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        } else if (association.getStatus() == Status.PENDING || association.getStatus() == Status.REJECTED) {
            if (shiftAvailabilityService.getByShiftId(associationEntity.getId().getShiftId()).getAvailableSlots() <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                association.setStatus(Status.ACCEPTED);
                associationService.save(association);
                emailService.sendNotificationViaEmail(associationEntity.getId().getCollaboratorId(), Status.ACCEPTED, associationEntity.getId().getShiftId());
                return new ResponseEntity<>(association, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping(ADMIN + "reject")
    public ResponseEntity<AssociationEntity> reject(@RequestBody AssociationEntity associationEntity) {
        AssociationEntity association = associationService.getByCollaboratorIdAndShiftId(associationEntity.getId().getCollaboratorId(), associationEntity.getId().getShiftId());

        if (association == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (association.getStatus() == Status.REJECTED) {
            return new ResponseEntity<>(association, HttpStatus.ALREADY_REPORTED);
        } else if (association.getStatus() == Status.PENDING || association.getStatus() == Status.ACCEPTED) {
            association.setStatus(Status.REJECTED);
            associationService.save(association);
            emailService.sendNotificationViaEmail(associationEntity.getId().getCollaboratorId(), Status.REJECTED, associationEntity.getId().getShiftId());
            return new ResponseEntity<>(association, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
