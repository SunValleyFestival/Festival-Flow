package com.sunvalley.festivalFlowbe.entity;

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
