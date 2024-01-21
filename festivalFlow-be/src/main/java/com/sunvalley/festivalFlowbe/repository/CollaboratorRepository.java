package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Integer> {

}
