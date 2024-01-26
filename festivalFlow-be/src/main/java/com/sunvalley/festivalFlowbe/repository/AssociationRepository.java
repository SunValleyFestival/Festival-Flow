package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.AssociationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<AssociationEntity, AssociationEntityId> {
}