package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftAvailabilityView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftAvailabilityRepository extends JpaRepository<ShiftAvailabilityView, Long> {

  ShiftAvailabilityView findByShiftId(int shiftId);
}
