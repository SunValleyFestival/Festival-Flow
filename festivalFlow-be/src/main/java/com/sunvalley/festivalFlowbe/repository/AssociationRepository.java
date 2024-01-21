package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<AssociationEntity, Integer> {

}
