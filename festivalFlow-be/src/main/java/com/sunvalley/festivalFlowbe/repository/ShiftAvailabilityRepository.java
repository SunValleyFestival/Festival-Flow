package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftAvailabilityRepository extends JpaRepository<ShiftAvailabilityView, Long> {

    ShiftAvailabilityView findByShiftId(int shiftId);

    @Query("SELECT SUM(availableSlots) as total_available_slots from shift_availability where locationId = ?1")
    Integer getAvailableSlotsByLocationId(int locationId);
}
