package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {

}
