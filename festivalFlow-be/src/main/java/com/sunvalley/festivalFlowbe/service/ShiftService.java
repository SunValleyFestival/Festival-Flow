package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.repository.ShiftRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;


    public List<ShiftEntity> getShiftsByLocationId(int id) {
        return shiftRepository.findByLocationId(id);
    }

    public List<ShiftEntity> getAll() {
        return shiftRepository.findAll();
    }

    public ShiftEntity getById(int id) {
        return shiftRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        shiftRepository.deleteById(id);
    }

    public ShiftEntity create(ShiftEntity shift) {
        return shiftRepository.save(shift);
    }


}
