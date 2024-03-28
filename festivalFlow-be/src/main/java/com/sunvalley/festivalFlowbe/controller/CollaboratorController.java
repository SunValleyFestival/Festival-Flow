package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


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

    @GetMapping(COLLABORATOR + "all")
    public ResponseEntity<List<CollaboratorEntity>> getAll() {
        List<CollaboratorEntity> collaborators = collaboratorService.getAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
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
