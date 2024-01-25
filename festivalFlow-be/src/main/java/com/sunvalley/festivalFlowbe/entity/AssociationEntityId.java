package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AssociationEntityId implements Serializable {
    private static final long serialVersionUID = 3325111509344544390L;
    @Column(name = "shift_id", nullable = false)
    private Integer shiftId;

    @Column(name = "collaborator_id", nullable = false)
    private Integer collaboratorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;
        AssociationEntityId entity = (AssociationEntityId) o;
        return Objects.equals(this.shiftId, entity.shiftId) &&
                Objects.equals(this.collaboratorId, entity.collaboratorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftId, collaboratorId);
    }

}