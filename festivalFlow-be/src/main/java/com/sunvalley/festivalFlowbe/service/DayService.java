package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {
    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public DayEntity getDayById(int id) {
        return dayRepository.findById(id).orElse(null);
    }

    public List<DayEntity> getAll() {
        return dayRepository.findAll();
    }

    public DayEntity getById(final int id) {
        return dayRepository.findById(id).orElse(null);
    }

    public void deleteById(final int id) {
        dayRepository.deleteById(id);
    }

    public DayEntity create(DayEntity day) {
        return dayRepository.save(day);
    }
}
