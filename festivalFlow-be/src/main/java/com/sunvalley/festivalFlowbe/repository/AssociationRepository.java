package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.AssociationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssociationRepository extends JpaRepository<AssociationEntity, AssociationEntityId> {

    @Query("SELECT a FROM AssociationEntity a WHERE a.collaborator_id = ?1")
    AssociationEntity findByCollaboratorId(int id);

}