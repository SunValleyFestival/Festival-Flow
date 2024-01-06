package com.sunvalley.festivalFlowbe.entity;

import java.io.Serializable;
import java.util.Objects;

public class AssociationEntityPK implements Serializable {
    private int shiftId;
    private int collaboratorId;

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(int collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationEntityPK that = (AssociationEntityPK) o;
        return shiftId == that.shiftId && collaboratorId == that.collaboratorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftId, collaboratorId);
    }
}
