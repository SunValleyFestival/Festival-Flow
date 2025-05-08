package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class LocationController {
  private static final String LOCATION = "user/location/";

  @Value("${image.upload.token}")
  private String token;

  @Value("${image.upload.url}")
  private String imageUpload;

  private final LocationService locationService;
  private final CollaboratorService collaboratorService;
  private final JWTTokenProviderService jwtTokenProviderService;

  @GetMapping(LOCATION + "{id}")
  public ResponseEntity<LocationEntity> getById(@RequestHeader("Authorization") String token, @PathVariable int id) throws ParseException {
    LocationEntity location = locationService.getById(id, isMinor(token));
    return new ResponseEntity<>(location, HttpStatus.OK);
  }

  @GetMapping(LOCATION + "day/{day}")
  public ResponseEntity<List<LocationEntity>> getByDayId(@RequestHeader("Authorization") String token, @PathVariable int day) throws ParseException {
    List<LocationEntity> locations = locationService.getLocationsByDayId(day, isMinor(token));
    return new ResponseEntity<>(locations, HttpStatus.OK);
  }

  private boolean isMinor(String token) throws ParseException {
    return collaboratorService.isMinor(jwtTokenProviderService.getUserIdFromToken(token));
  }

  @GetMapping(LOCATION)
  public ResponseEntity<List<LocationEntity>> getAll() throws ParseException {
    List<LocationEntity> locations = locationService.getAll();
    return new ResponseEntity<>(locations, HttpStatus.OK);
  }

}
