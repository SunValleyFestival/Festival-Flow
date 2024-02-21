package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

    @Query("select s from ShiftEntity s where s.location = ?1")
    List<ShiftEntity> findByLocation(LocationEntity location);
}