package com.sunvalley.festivalFlowbe.service;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();


    @Autowired
    public CollaboratorService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    public List<CollaboratorEntity> getAll() {
        return collaboratorRepository.findAll();
    }

    public void createIfExistByEmail(AuthEntity authEntity) {
        if (null == collaboratorRepository.getIdByEmail(authEntity.getEmail())) {
            CollaboratorEntity collaborator = new CollaboratorEntity();
            collaborator.setEmail(authEntity.getEmail());
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

    public List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulated() {
        return collaboratorRepository.findCollaboratorEntitiesWhereIsPopulated();
    }

    public List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulatedAndAssociationAccepted() {
        return collaboratorRepository.findCollaboratorEntitiesWhereIsPopulatedAndAssociationAccepted();
    }

    public List<CollaboratorEntity> findCollaboratorEntitiesWhereIsPopulatedAndAssociationAcceptedByShiftId(int shiftId) {
        return collaboratorRepository.findCollaboratorEntitiesWhereIsPopulatedAndAssociationAcceptedByShiftId(shiftId);
    }

    public boolean phoneIsValid(String phone) {
        return phoneNumberUtil.isPossibleNumber(phone, "CH") || phoneNumberUtil.isPossibleNumber(phone, "IT");

    }

    public boolean existsByEmail(final String email) {
        return collaboratorRepository.existsByEmail(email);
    }
    public boolean isMinor(int id) {
        try {
            Date dataNascita = collaboratorRepository.findById(id).get().getAge();
            long differenzaMillisecondi = new Date().getTime() - dataNascita.getTime();
            long anni = differenzaMillisecondi / (1000L * 60 * 60 * 24 * 365);
            return anni < 18;
        } catch (Exception e) {
            return false;
        }

    }

}
