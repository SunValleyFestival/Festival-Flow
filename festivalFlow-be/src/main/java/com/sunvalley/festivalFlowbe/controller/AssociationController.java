package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival-flow/association")
public class AssociationController {

    private final AssociationService associationService;

    private final LocationService locationService;

    @Autowired
    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<List<AssociationEntity>> getAll() {
        List<AssociationEntity> associations = associationService.getAll();
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/userId/{id}")
    public ResponseEntity<List<LocationEntity>> getByTypeAndId(String type, int id) {
        List<LocationEntity> locationEntities;
        locationEntities =  locationService.getloca associationService.getByUserId(id);
            return new ResponseEntity<>(associations, HttpStatus.OK);

        List<AssociationEntity> associations = associationService.getByTypeAndId(type, id);
        return new ResponseEntity<>(associations, HttpStatus.OK);
    }
}
