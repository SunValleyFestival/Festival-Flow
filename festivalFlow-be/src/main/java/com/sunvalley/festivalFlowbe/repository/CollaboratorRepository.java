package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Integer> {

    @Query("select c.email from CollaboratorEntity c where c.id = ?1")
    String getEmailById(int id);

    @Query("select c from CollaboratorEntity c where c.email = ?1")
    CollaboratorEntity getIdByEmail(String email);

    @Query("select c from CollaboratorEntity c join AssociationEntity a on c.id = a.id.collaboratorId where a.id.shiftId = ?1")
    List<CollaboratorEntity> findByShiftId(int shiftId);

    @Query("select c from CollaboratorEntity c where c.age is not null and c.size is not null and c.town is not null and c.yearsExperience is not null and c.phone is not null and c.firstName is not null and c.lastName is not null and c.email is not null")
    List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulated();

    @Query("select c from CollaboratorEntity c join AssociationEntity a on c.id = a.id.collaboratorId where a.status = 2 and c.age is not null and c.size is not null and c.town is not null and c.yearsExperience is not null and c.phone is not null and c.firstName is not null and c.lastName is not null and c.email is not null")
    List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulatedAndAssociationAccepted();

    @Query("select c from CollaboratorEntity c join AssociationEntity a on c.id = a.id.collaboratorId where a.status = 2 and c.age is not null and c.size is not null and c.town is not null and c.yearsExperience is not null and c.phone is not null and c.firstName is not null and c.lastName is not null and c.email is not null and a.id.shiftId = ?1")
    List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulatedAndAssociationAcceptedByShiftId(int shiftId);

    boolean existsByEmail(String email);
}