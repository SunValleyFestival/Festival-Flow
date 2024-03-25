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

  private static final String ADMIN = "admin/location/";
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

  @PostMapping(ADMIN + "create")
  public ResponseEntity<LocationEntity> create(@RequestBody LocationEntity location) {
    location.setId(null);
    LocationEntity newLocation = locationService.create(location);
    return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
  }

  @PostMapping(value = ADMIN + "upload-image/{locationId}", consumes = { "multipart/form-data" })
  public ResponseEntity<String> handleFileUpload(@RequestParam("image") MultipartFile file, @PathVariable int locationId) throws IOException {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("token", token);
    body.add("file", file.getResource());

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<String> responseEntity = restTemplate.exchange(imageUpload, HttpMethod.POST, requestEntity, String.class);

    var responseBody = responseEntity.getBody();
    if (responseBody == null) {
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
    String url = responseBody.split(": ")[1];

    LocationEntity location = locationService.getById(locationId, false);
    location.setImage(url);
    locationService.update(location);

    return new ResponseEntity<>(url, HttpStatus.OK);
  }

  @DeleteMapping(ADMIN)
  public ResponseEntity<LocationEntity> deleteById(@RequestBody LocationEntity locationEntity) {
    locationService.deleteById(locationEntity.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(ADMIN + "update")
  public ResponseEntity<LocationEntity> update(@RequestBody LocationEntity location) {
    LocationEntity updatedLocation = locationService.update(location);
    return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
  }

  private boolean isMinor(String token) throws ParseException {
    return collaboratorService.isMinor(jwtTokenProviderService.getUserIdFromToken(token));
  }

}
