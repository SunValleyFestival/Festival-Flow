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

    public void createIfExistByEmail(String email) {
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

    public List<CollaboratorEntity> getByShiftId(int shiftId) {
        return collaboratorRepository.findByShiftId(shiftId);
    }

    public CollaboratorEntity getById(int id) {
        return collaboratorRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        collaboratorRepository.deleteById(id);
    }

    public CollaboratorEntity update(CollaboratorEntity collaborator) {
        return collaboratorRepository.save(collaborator);
    }

}
