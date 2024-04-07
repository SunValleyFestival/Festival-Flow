package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class CollaboratorController {

    private static final String COLLABORATOR = "user/collaborator/";

    private final CollaboratorService collaboratorService;
    private final JWTTokenProviderService jwtTokenProviderService;
    private final EmailService emailService;

    @GetMapping(COLLABORATOR)
    public ResponseEntity<CollaboratorEntity> get(@RequestHeader("Authorization") String token) throws ParseException {
        CollaboratorEntity collaborator = collaboratorService.getById(jwtTokenProviderService.getUserIdFromToken(token));
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @GetMapping(COLLABORATOR + "{id}")
    public ResponseEntity<CollaboratorEntity> getById(@PathVariable int id) {
        CollaboratorEntity collaborator = collaboratorService.getById(id);
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @PutMapping(COLLABORATOR + "update")
    public ResponseEntity<CollaboratorEntity> update(@RequestBody CollaboratorEntity collaborator, @RequestHeader("Authorization") String token) throws ParseException {
        collaborator.setEmail(collaboratorService.getEmailById(collaborator.getId()));
        if (!jwtTokenProviderService.getUserIdFromToken(token).equals(collaborator.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            if (collaboratorService.phoneIsValid(collaborator.getPhone())) {
                CollaboratorEntity newCollaborator = collaboratorService.update(collaborator);
                emailService.sendCollabortorInfo(newCollaborator.getId());
                return new ResponseEntity<>(newCollaborator, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
    }

}
