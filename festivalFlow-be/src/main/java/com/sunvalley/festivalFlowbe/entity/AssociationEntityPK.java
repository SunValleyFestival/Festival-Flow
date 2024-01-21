package com.sunvalley.festivalFlowbe.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class AssociationEntityPK implements Serializable {
    private int shiftId;
    private int collaboratorId;
}
