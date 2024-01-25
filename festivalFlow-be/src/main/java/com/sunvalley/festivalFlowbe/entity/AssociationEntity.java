package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "association", schema = "FestivalFlow")
public class AssociationEntity {
    @EmbeddedId
    private AssociationEntityId id;

    @Column(name = "status", nullable = false)
    private Integer status;

}