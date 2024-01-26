package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.repository.ShiftRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {

  private final ShiftRepository shiftRepository;

  @Autowired
  public ShiftService(ShiftRepository shiftRepository) {
    this.shiftRepository = shiftRepository;
  }

  public List<ShiftEntity> getAll() {
    return shiftRepository.findAll();
  }
}
