package com.sunvalley.festivalFlowbe.controller.admin;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class CollaboratorAdminController {

    private static final String ADMIN = "admin/collaborator/";

    private final CollaboratorService collaboratorService;


    @GetMapping(ADMIN + "{id}")
    public ResponseEntity<CollaboratorEntity> getById(@PathVariable int id) {
        CollaboratorEntity collaborator = collaboratorService.getById(id);
        return new ResponseEntity<>(collaborator, HttpStatus.OK);
    }

    @GetMapping(ADMIN + "all")
    public ResponseEntity<List<CollaboratorEntity>> getAll() {
        List<CollaboratorEntity> collaborators = collaboratorService.getAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

  @CrossOrigin
  @PutMapping(ADMIN + "update")
  public ResponseEntity<CollaboratorEntity> updateAdmin(@RequestBody CollaboratorEntity collaborator) {
    CollaboratorEntity newCollaborator = collaboratorService.update(collaborator);
    return new ResponseEntity<>(newCollaborator, HttpStatus.OK);
  }

    @GetMapping(ADMIN)
    public ResponseEntity<List<CollaboratorEntity>> getAllAdmin() {
        List<CollaboratorEntity> collaborators = collaboratorService.findCollaboratorEntitiesWhereIsPopulated();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    @DeleteMapping(ADMIN)
    public ResponseEntity<CollaboratorEntity> deleteById(@RequestBody CollaboratorEntity collaboratorEntity) {
        collaboratorService.deleteById(collaboratorEntity.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
