package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

}
