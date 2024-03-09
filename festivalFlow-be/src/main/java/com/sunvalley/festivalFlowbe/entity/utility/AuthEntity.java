package com.sunvalley.festivalFlowbe.entity.utility;


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
    private String code;
    private String token;
    private boolean emailSended;
    private boolean isValid;
}