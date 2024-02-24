package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

    List<ShiftEntity> findByLocationId(int location);

    List<ShiftEntity> findByLocationIdAndDay(int location, int day);

}