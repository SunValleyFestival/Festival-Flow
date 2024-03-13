package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShiftEntityAdmin extends ShiftEntity {

    @OneToMany(mappedBy = "shiftEntityAdmin", cascade = CascadeType.ALL) // For persistence of collaborators
    private List<CollaboratorEntity> collaboratorEntityList;

    public ShiftEntityAdmin(ShiftEntity shiftEntity, List<CollaboratorEntity> collaboratorEntityList) {
        super(shiftEntity.getId(), shiftEntity.getDescription(), shiftEntity.getName(),
                shiftEntity.getLocation(), shiftEntity.getStartTime(), shiftEntity.getEndTime(),
                shiftEntity.getMaxCollaborator(), shiftEntity.isAdultsOnly());
        this.collaboratorEntityList = collaboratorEntityList;
    }
}
