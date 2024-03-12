package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
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
@RequestMapping("/festival-flow")
public class CollaboratorController {

    private static final String ADMIN = "/admin/collaborator/";
    private static final String COLLABORATOR = "/user/collaborator/";

    private final CollaboratorService collaboratorService;
    private JWTTokenProviderService jwtTokenProviderService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @CrossOrigin
    @GetMapping(COLLABORATOR)
    public ResponseEntity<CollaboratorEntity> get(@RequestHeader("Authorization") String token) throws ParseException {
        CollaboratorEntity collaborator = collaboratorService.getById(jwtTokenProviderService.getUserIdFromToken(token));
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(COLLABORATOR + "update")
    public ResponseEntity<CollaboratorEntity> update(@RequestBody CollaboratorEntity collaborator, @RequestHeader("Authorization") String token) throws ParseException {
        if (!jwtTokenProviderService.getUserIdFromToken(token).equals(collaborator.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            CollaboratorEntity newCollaborator = collaboratorService.update(collaborator);
            return new ResponseEntity<>(newCollaborator, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping(ADMIN + "shift/{shiftId}")
    public ResponseEntity<List<CollaboratorEntity>> getByShiftId(@PathVariable int shiftId) {
        List<CollaboratorEntity> collaborators = collaboratorService.getByShiftId(shiftId);
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }


    @CrossOrigin
    @DeleteMapping(ADMIN)
    public ResponseEntity<CollaboratorEntity> deleteById(@RequestBody CollaboratorEntity collaboratorEntity) {
        collaboratorService.deleteById(collaboratorEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
