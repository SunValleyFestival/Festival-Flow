package com.sunvalley.festivalFlowbe.entity;

import java.util.List;

public class ShiftEntityAdmin extends ShiftEntity {

    private List<CollaboratorEntity> collaboratorEntityList;

    public ShiftEntityAdmin(ShiftEntity shiftEntity, List<CollaboratorEntity> collaboratorEntityList) {
        super(shiftEntity.getId(), shiftEntity.getDescription(), shiftEntity.getName(),
                shiftEntity.getLocation(), shiftEntity.getStartTime(), shiftEntity.getEndTime(),
                shiftEntity.getMaxCollaborator(), shiftEntity.isAdultsOnly());
        this.collaboratorEntityList = collaboratorEntityList;
    }


    public List<CollaboratorEntity> getCollaboratorEntityList() {
        return collaboratorEntityList;
    }

    public void setCollaboratorEntityList(List<CollaboratorEntity> collaboratorEntityList) {
        this.collaboratorEntityList = collaboratorEntityList;
    }

}
