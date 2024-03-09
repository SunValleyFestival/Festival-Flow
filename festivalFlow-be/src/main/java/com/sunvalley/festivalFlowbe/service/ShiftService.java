package com.sunvalley.festivalFlowbe.service;

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
        return shiftRepository.findByLocationId(id);
    }

    public List<ShiftEntity> getAll() {
        return shiftRepository.findAll();
    }

    public ShiftEntity getById(int id) {
        return shiftRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        shiftRepository.deleteById(id);
    }
}
