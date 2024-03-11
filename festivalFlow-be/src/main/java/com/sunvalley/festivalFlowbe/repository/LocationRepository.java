package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    @Query("select l from LocationEntity l where l.day = ?1")
    List<LocationEntity>findByDay(DayEntity day);
}