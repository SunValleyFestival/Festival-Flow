package com.sunvalley.festivalFlowbe.entity.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class EmailRequest {

  private String to;
  private String subject;
  private String message;
}