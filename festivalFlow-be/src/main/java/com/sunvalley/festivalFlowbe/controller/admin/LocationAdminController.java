package com.sunvalley.festivalFlowbe.controller.admin;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival-flow/")
public class LocationAdminController {

  private static final String ADMIN = "admin/location/";

  @Value("${image.upload.token}")
  private String token;

  @Value("${image.upload.url}")
  private String imageUpload;

  private final LocationService locationService;
  private final CollaboratorService collaboratorService;
  private final JWTTokenProviderService jwtTokenProviderService;

  @GetMapping(ADMIN + "{id}")
  public ResponseEntity<LocationEntity> getById(@PathVariable int id) {
    LocationEntity location = locationService.getById(id, false);
    return new ResponseEntity<>(location, HttpStatus.OK);
  }

  @GetMapping( ADMIN+ "day/{day}")
  public ResponseEntity<List<LocationEntity>> getByDayId(@PathVariable int day) {
    List<LocationEntity> locations = locationService.getLocationsByDayId(day, false);
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

}
