package com.sunvalley.festivalFlowbe.service;

import com.sunvalley.festivalFlowbe.entity.AssociationEntity;
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

  public List<AssociationEntity> getAll() {
    return associationRepository.findAll();
  }
}
