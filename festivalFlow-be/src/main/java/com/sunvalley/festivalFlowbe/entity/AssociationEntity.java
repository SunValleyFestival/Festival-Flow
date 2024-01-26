package com.sunvalley.festivalFlowbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "association", schema = "FestivalFlow")
public class AssociationEntity {
    @EmbeddedId
    private AssociationEntityId id;

    @Column(name = "status", nullable = false)
    private Integer status;

}