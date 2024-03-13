package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.repository.AssociationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Integer countByShiftIdAndStatusNotReject(int shiftId) {
    return associationRepository.countByShiftIdAndStatusNotReject(shiftId);
  }

    public List<Integer> getByUserId(int userId) {
        return associationRepository.findByUserId(userId);
    }

  public List<AssociationEntity> getByShiftId(final int locationId) {
    return associationRepository.findByShiftId(locationId);
  }

  public List<CollaboratorEntity> getCollaboratorsByShiftId(final int shiftId) {
    return associationRepository.getCollaboratorsByShiftId(shiftId);
  }
}
