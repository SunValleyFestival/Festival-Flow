package com.sunvalley.festivalFlowbe.entity;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociationEntityPK implements Serializable {
    private int shiftId;
    private int collaboratorId;
}
