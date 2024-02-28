package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "shift_availability")
@Data
public class ShiftAvailabilityView {

  @Id
  @Column(name = "shift_id")
  private int shiftId;

  @Column(name = "available_slots")
  private int availableSlots;
}
