package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

    List<ShiftEntity> findByLocationId(int location);

}