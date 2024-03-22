package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import com.sunvalley.festivalFlowbe.repository.ShiftAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftAvailabilityService {

  private final ShiftAvailabilityRepository shiftAvailabilityRepository;

  public ShiftAvailabilityView getByShiftId(int shiftId) {
    return shiftAvailabilityRepository.findByShiftId(shiftId);
  }

  public ShiftAvailabilityView getAvailableSlotsByLocationId(int locationId) {
    var availableSlots = shiftAvailabilityRepository.getAvailableSlotsByLocationId(locationId);
    var shiftAvailability = new ShiftAvailabilityView();
    shiftAvailability.setAvailableSlots(availableSlots);
    shiftAvailability.setLocationId(locationId);
    return shiftAvailability;
  }
}
