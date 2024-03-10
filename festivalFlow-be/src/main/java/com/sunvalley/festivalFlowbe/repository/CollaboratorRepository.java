package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Integer> {

    @Query("select c.email from CollaboratorEntity c where c.id = ?1")
    String getEmailById(int id);

    @Query("select c from CollaboratorEntity c where c.email = ?1")
    CollaboratorEntity getIdByEmail(String email);
}