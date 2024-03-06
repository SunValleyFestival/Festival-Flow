package com.sunvalley.festivalFlowbe.controller.token;

import com.nimbusds.jose.JOSEException;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.service.VerificationCodeService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JWTTokenProviderService tokenProvider;

    private final VerificationCodeService verificationCodeService = new VerificationCodeService();

    @PostMapping("/login")
    public String login(@RequestBody AuthEntity authEntity) {
        log.info("userId: " + authEntity.getUserId());
        verificationCodeService.createCode((long) authEntity.getUserId());
        verificationCodeService.logCode((long) authEntity.getUserId());
        return "code sent to email";
    }

    @PostMapping("/login/confirm")
    public String loginConfirm(@RequestBody AuthEntity authEntity) {
        if (verificationCodeService.isvalid((long) authEntity.getUserId(), authEntity.getCode())) {
            return tokenProvider.generateToken();
        } else {
            return "invalid code";
        }
    }

    @PostMapping("/validate")
    public AuthEntity validateToken(@RequestBody AuthEntity authEntity) {
        try {
            if (tokenProvider.validateToken(authEntity.getToken())) {
                authEntity.setValid(true);
            } else {
                authEntity.setValid(false);
            }
        } catch (JOSEException | ParseException e) {
            authEntity.setValid(false);
        }
        return authEntity;
    }

}
