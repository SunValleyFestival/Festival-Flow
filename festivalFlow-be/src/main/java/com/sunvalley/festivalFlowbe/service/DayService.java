package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.repository.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DayService {
    private final DayRepository dayRepository;

    public DayEntity getDayById(int id) {
        return dayRepository.findById(id).orElse(null);
    }

    public List<DayEntity> getAll() {
        return dayRepository.findAll();
    }

    public DayEntity getById(final int id) {
        return dayRepository.findById(id).orElse(null);
    }

    public DayEntity getByShiftId(final int shiftId) {
        return dayRepository.findByShiftId(shiftId);
    }

    public void deleteById(final int id) {
        dayRepository.delete(Objects.requireNonNull(dayRepository.findById(id).orElse(null)));
    }

    public DayEntity create(DayEntity day) {
        return dayRepository.save(day);
    }
}
