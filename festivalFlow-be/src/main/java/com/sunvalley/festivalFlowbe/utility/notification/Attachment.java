package com.sunvalley.festivalFlowbe.utility.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class Attachment {

  private String filename;
  private byte[] content;
}