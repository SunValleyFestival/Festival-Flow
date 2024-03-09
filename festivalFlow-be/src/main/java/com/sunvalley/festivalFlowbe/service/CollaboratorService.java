package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.repository.CollaboratorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void createIfExistByEmail( String email) {
    if (null == collaboratorRepository.getIdByEmail(email)) {
      CollaboratorEntity collaborator = new CollaboratorEntity();
      collaborator.setEmail(email);
      collaboratorRepository.save(collaborator);
    }
  }

  public int getIdByEmail(String email) {
    return collaboratorRepository.getIdByEmail(email).getId();
  }

  public String getEmailById(int id) {
    return collaboratorRepository.getEmailById(id);
  }
}
