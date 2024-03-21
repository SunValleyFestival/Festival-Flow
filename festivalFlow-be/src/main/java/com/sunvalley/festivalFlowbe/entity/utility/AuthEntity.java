package com.sunvalley.festivalFlowbe.entity.utility;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class AuthEntity {
    private int userId;
    private String email;
  private Date date;
    private String code;
    private String token;
    private boolean emailSended;
    private boolean isValid;
}
