package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
}