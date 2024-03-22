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

    private final CollaboratorService collaboratorService;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, LocationService locationService, CollaboratorService collaboratorService) {
        this.shiftRepository = shiftRepository;
        this.locationService = locationService;
        this.collaboratorService = collaboratorService;
    }


    public List<ShiftEntity> getShiftsByLocationId(int id) {
        return shiftRepository.findByLocationId(id);
    }

    public List<ShiftEntity> finAllByLocationIdAndOnlyAdult(int id, boolean onlyAdult) {
        return shiftRepository.findAllByLocationIdAndOnlyAdult(id, onlyAdult);
    }

    public List<ShiftEntity> getAll() {
        return shiftRepository.findAll();
    }

    public ShiftEntity getById(int id) {
        return shiftRepository.findById(id).orElse(null);
    }

    public ShiftEntity getByIdOnlyAdult(int id) {
        return shiftRepository.findByIdOnlyAduldt(id);
    }

    public void deleteById(int id) {
        shiftRepository.deleteById(id);
    }

    public ShiftEntity create(ShiftEntity shift) {
        return shiftRepository.save(shift);
    }


}
