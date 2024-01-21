package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<DayEntity, Integer> {

}
