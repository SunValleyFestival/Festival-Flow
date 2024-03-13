package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.AssociationEntityId;
import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssociationRepository extends JpaRepository<AssociationEntity, AssociationEntityId> {

    @Query("SELECT a FROM AssociationEntity a WHERE a.id.collaboratorId = ?1")
    List<AssociationEntity> findByCollaboratorId(int id);

    @Query("SELECT a FROM AssociationEntity a WHERE a.id.collaboratorId = ?1 AND a.id.shiftId = ?2")
    AssociationEntity findByIdCollaboratorIdAndIdShiftId(int collaboratorId, int shiftId);

  @Query("SELECT a FROM AssociationEntity a WHERE a.id.collaboratorId = ?1")
  List<Integer> findByUserId(int userId);

    @Query("SELECT COUNT(a) FROM AssociationEntity a WHERE a.id.shiftId = ?1 AND a.status != 2")
    Integer countByShiftIdAndStatusNotReject(int shiftId);

  @Query("SELECT a FROM AssociationEntity a WHERE a.id.shiftId = ?1")
  List<AssociationEntity> findByShiftId(int locationId);

  @Query("select c from CollaboratorEntity c join AssociationEntity a on c.id = a.id.collaboratorId where a.id.shiftId = ?1")
  List<CollaboratorEntity> getCollaboratorsByShiftId(int shiftId);
}