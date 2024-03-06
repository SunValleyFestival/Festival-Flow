package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;

  @Autowired
  public CollaboratorService(CollaboratorRepository collaboratorRepository) {
    this.collaboratorRepository = collaboratorRepository;
  }


  public List<CollaboratorEntity> getAll() {
    return collaboratorRepository.findAll();
  }
}
