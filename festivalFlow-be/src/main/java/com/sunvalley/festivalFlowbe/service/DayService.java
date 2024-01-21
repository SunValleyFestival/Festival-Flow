package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.repository.DayRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayService {
  private final DayRepository dayRepository;

  @Autowired
  public DayService(DayRepository dayRepository) {
    this.dayRepository = dayRepository;
  }

  public List<DayEntity> getAll(){
    return dayRepository.findAll();
  }

}
