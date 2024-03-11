package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Integer> {

    @Query("select c.email from CollaboratorEntity c where c.id = ?1")
    String getEmailById(int id);

    @Query("select c from CollaboratorEntity c where c.email = ?1")
    CollaboratorEntity getIdByEmail(String email);

    //get collaborator by shift id join association
    @Query("select c from CollaboratorEntity c join AssociationEntity a on c.id = a.id.collaboratorId where a.id.shiftId = ?1")
    List<CollaboratorEntity> findByShiftId(int shiftId);
}