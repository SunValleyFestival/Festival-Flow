package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    private final DayService dayService;


    @Autowired
    public LocationService(LocationRepository locationRepository, DayService dayService) {
        this.locationRepository = locationRepository;
        this.dayService = dayService;
    }

    public List<LocationEntity> getAll() {
        return locationRepository.findAll();
    }

    public LocationEntity create(LocationEntity location) {
        return locationRepository.save(location);
    }

  public List<LocationEntity> getLocationsById(int id) {
    return locationRepository.findById(id);
  }

    public List<LocationEntity> getLocationsByDayId(int id) {
        DayEntity day = dayService.getDayById(id);
        return locationRepository.findByDay(day);
    }

    public LocationEntity getById(int id) {
        return locationRepository.findById(id).orElse(null);
    }
}
