package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.AssociationAdmin;
import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.repository.AssociationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociationService {

  private final AssociationRepository associationRepository;
  private final CollaboratorService collaboratorService;

  @Autowired
  public AssociationService(AssociationRepository associationRepository, CollaboratorService collaboratorService) {
    this.associationRepository = associationRepository;
    this.collaboratorService = collaboratorService;
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

  public List<AssociationAdmin> getAssociationAdminByShiftId(final int shiftId) {
    var associationsAdmin = new ArrayList<AssociationAdmin>();
    var associations = this.getByShiftId(shiftId);

    for (AssociationEntity association : associations) {
      var collaborator = collaboratorService.getById(association.getId().getCollaboratorId());
      var associationAdmin = new AssociationAdmin(collaborator, association.getId().getShiftId(), association.getStatus());
      associationsAdmin.add(associationAdmin);
    }

    return associationsAdmin;
  }
}
