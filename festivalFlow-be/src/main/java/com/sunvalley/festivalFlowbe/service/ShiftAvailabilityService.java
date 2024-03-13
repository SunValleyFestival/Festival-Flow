package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import com.sunvalley.festivalFlowbe.repository.ShiftAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftAvailabilityService {

    private final ShiftAvailabilityRepository shiftAvailabilityRepository;

    @Autowired
    public ShiftAvailabilityService(ShiftAvailabilityRepository shiftAvailabilityRepository) {
        this.shiftAvailabilityRepository = shiftAvailabilityRepository;
    }

    public ShiftAvailabilityView getByShiftId(int shiftId) {
        return shiftAvailabilityRepository.findByShiftId(shiftId);
    }

    public Integer getAvailableSlotsByLocationId(int locationId) {
        return shiftAvailabilityRepository.getAvailableSlotsByLocationId(locationId);
    }
}
