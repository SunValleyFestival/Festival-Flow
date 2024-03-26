package com.sunvalley.festivalFlowbe.entity.utility;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssociationAdmin {

  private CollaboratorEntity collaborator;
  private Integer shiftId;
  private Status status;
  private String comment;

}
