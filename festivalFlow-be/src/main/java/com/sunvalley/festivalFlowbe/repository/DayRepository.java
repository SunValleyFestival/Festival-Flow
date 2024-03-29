package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DayRepository extends JpaRepository<DayEntity, Integer> {

    //SELECT d.name FROM day d inner join location l inner join shift s where s.id = 20 and l.id = s.location_id and d.id = l.day_id;

    @Query("SELECT d FROM DayEntity d inner join LocationEntity l inner join ShiftEntity s where s.id= :shiftId and l = s.location and d = l.day")
    DayEntity findByShiftId(int shiftId);
}