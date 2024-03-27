package com.sunvalley.festivalFlowbe.controller.admin;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.entity.utility.AssociationAdmin;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import com.sunvalley.festivalFlowbe.service.ShiftAvailabilityService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class AssociationAdminController {

    private static final String ADMIN = "admin/association/";

    private final AssociationService associationService;
    private final ShiftAvailabilityService shiftAvailabilityService;
    private final EmailService emailService;


    @CrossOrigin
    @GetMapping(ADMIN + "shift/{shiftId}")
    public ResponseEntity<List<AssociationAdmin>> getCollaboratorsByShiftId(@PathVariable int shiftId) {
        List<AssociationAdmin> associations = associationService.getAssociationAdminByShiftId(shiftId);
        return new ResponseEntity<>(associations, HttpStatus.OK);
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
