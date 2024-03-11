package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationService {

  private final AssociationRepository associationRepository;

  @Autowired
  public AssociationService(AssociationRepository associationRepository) {
    this.associationRepository = associationRepository;
  }

  public List<AssociationEntity> getByCollaboratorId(int id) {
    return associationRepository.findByCollaboratorId(id);
  }

  public AssociationEntity getByCollaboratorIdAndShiftId(int collaboratorId, int shiftId) {
    return associationRepository.findByIdCollaboratorIdAndIdShiftId(collaboratorId, shiftId);
  }

  public List<AssociationEntity> getAll() {
    return associationRepository.findAll();
  }

  public void save(AssociationEntity association) {
    associationRepository.save(association);
  }


}
