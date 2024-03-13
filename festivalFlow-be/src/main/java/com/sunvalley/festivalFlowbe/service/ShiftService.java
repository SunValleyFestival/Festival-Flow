package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntityAdmin;
import com.sunvalley.festivalFlowbe.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ShiftEntity> getAll() {
        return shiftRepository.findAll();
    }

    public ShiftEntity getById(int id) {
        return shiftRepository.findById(id).orElse(null);
    }

    public List<ShiftEntityAdmin> getShiftAdmin(List<ShiftEntity> shiftList) {
        List<ShiftEntityAdmin> shiftEntityAdminList = new ArrayList<>(); // Create an empty list to store ShiftEntityAdmin objects

        for (ShiftEntity shift : shiftList) {
            List<CollaboratorEntity> collaboratorEntityList = collaboratorService.getByShiftId(shift.getId());
            shiftEntityAdminList.add(new ShiftEntityAdmin(shift, collaboratorEntityList));
        }

        return shiftEntityAdminList;
    }

    public void deleteById(int id) {
        shiftRepository.deleteById(id);
    }

    public ShiftEntity create(ShiftEntity shift) {
        return shiftRepository.save(shift);
    }


}
