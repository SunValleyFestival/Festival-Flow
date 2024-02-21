package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {

  private final ShiftRepository shiftRepository;

  private final LocationService locationService;

  @Autowired
  public ShiftService(ShiftRepository shiftRepository, LocationService locationService) {
    this.shiftRepository = shiftRepository;
    this.locationService = locationService;
  }


  public List<ShiftEntity> getShiftsByLocationId(int id) {
    LocationEntity location = locationService.getById(id);
    return shiftRepository.findByLocation(location);
  }

  public List<ShiftEntity> getAll() {
    return shiftRepository.findAll();
  }
}
