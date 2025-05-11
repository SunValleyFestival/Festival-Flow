package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.ShiftAvailabilityService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import com.sunvalley.festivalFlowbe.service.utility.ConfigurationService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class AssociationController {

  private static final String ASSOCIATION = "user/association/";

  private final AssociationService associationService;
  private final ShiftService shiftService;
  private final CollaboratorService collaboratorService;
  private final ShiftAvailabilityService shiftAvailabilityService;
  private final EmailService emailService;
  private final JWTTokenProviderService jwtTokenProviderService;
  private final ConfigurationService configurationService;

  @CrossOrigin
  @GetMapping(ASSOCIATION + "collaborator-id/{id}")
  public ResponseEntity<List<AssociationEntity>> getByTypeAndId(@PathVariable int id){
    List<AssociationEntity> locationEntities;
    locationEntities = associationService.getByCollaboratorId(id);
    return new ResponseEntity<>(locationEntities, HttpStatus.OK);
  }

  @CrossOrigin
  @PostMapping(ASSOCIATION + "create")
  public ResponseEntity<AssociationEntity> create(@RequestBody AssociationEntity associationEntity, @RequestHeader("Authorization") String token) throws ParseException {
    if (!jwtTokenProviderService.getUserIdFromToken(token).equals(associationEntity.getId().getCollaboratorId()) || configurationService.getByName("lock")) {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    } else {
      if (associationService.getByCollaboratorIdAndShiftId(associationEntity.getId().getCollaboratorId(), associationEntity.getId().getShiftId()) != null) {
        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
      } else if (shiftAvailabilityService.getByShiftId(associationEntity.getId().getShiftId()).getAvailableSlots() <= 0) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      } else {
        if (shiftService.getById(associationEntity.getId().getShiftId()).getLocation().isAdultsOnly()) {
          if (collaboratorService.isMinor(associationEntity.getId().getCollaboratorId())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
          }
        }
        associationEntity.setStatus(Status.ACCEPTED);
        associationService.save(associationEntity);
        emailService.sendNotificationViaEmail(associationEntity.getId().getCollaboratorId(), Status.ACCEPTED, associationEntity.getId().getShiftId());
        return new ResponseEntity<>(associationEntity, HttpStatus.OK);
      }
    }
  }

  @CrossOrigin
  @PostMapping(ASSOCIATION + "create/user")
  public ResponseEntity<AssociationEntity> createUser(@RequestBody AssociationEntity associationEntity) throws ParseException {

    if (associationService.getByCollaboratorIdAndShiftId(associationEntity.getId().getCollaboratorId(), associationEntity.getId().getShiftId()) != null) {
      return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    } else if (shiftAvailabilityService.getByShiftId(associationEntity.getId().getShiftId()).getAvailableSlots() <= 0) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } else {
      if (shiftService.getById(associationEntity.getId().getShiftId()).getLocation().isAdultsOnly()) {
        if (collaboratorService.isMinor(associationEntity.getId().getCollaboratorId())) {
          return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
      }
      associationEntity.setStatus(Status.ACCEPTED);
      associationService.save(associationEntity);
      emailService.sendNotificationViaEmail(associationEntity.getId().getCollaboratorId(), Status.ACCEPTED, associationEntity.getId().getShiftId());
      return new ResponseEntity<>(associationEntity, HttpStatus.OK);
    }
  }

  @CrossOrigin
  @GetMapping(ASSOCIATION + "collaborators/{shiftId}")
  public ResponseEntity<List<String>> getCollaboratorsNameByShiftId(@PathVariable int shiftId) {
    List<String> collaboratorsName = associationService.getCollaboratorsNameByShiftId(shiftId);
    return new ResponseEntity<>(collaboratorsName, HttpStatus.OK);
  }

  @CrossOrigin
  @GetMapping(ASSOCIATION)
  public ResponseEntity<List<AssociationEntity>> getAssociations() {
    List<AssociationEntity> associations = associationService.getAll();
    return new ResponseEntity<>(associations, HttpStatus.OK);
  }
}
