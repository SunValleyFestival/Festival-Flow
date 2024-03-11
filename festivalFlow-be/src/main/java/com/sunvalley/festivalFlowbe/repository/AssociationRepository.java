package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.AssociationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssociationRepository extends JpaRepository<AssociationEntity, AssociationEntityId> {

    @Query("SELECT a FROM AssociationEntity a WHERE a.id.collaboratorId = ?1")
    List<AssociationEntity> findByCollaboratorId(int id);

    @Query("SELECT a FROM AssociationEntity a WHERE a.id.collaboratorId = ?1 AND a.id.shiftId = ?2")
    AssociationEntity findByIdCollaboratorIdAndIdShiftId(int collaboratorId, int shiftId);

}