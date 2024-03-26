package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

    List<ShiftEntity> findByLocationId(int location);

    @Query("SELECT s FROM ShiftEntity s JOIN LocationEntity l ON s.location.id = l.id WHERE l.adultsOnly=false and s.id=?1")
    ShiftEntity findByIdOnlyAduldt(int id);

    @Query("SELECT s FROM ShiftEntity s JOIN LocationEntity l ON s.location.id = l.id WHERE l.id=?1 and l.adultsOnly=?2")
    List<ShiftEntity> findAllByLocationIdAndOnlyAdult(int locationId, boolean onlyAdult);

}