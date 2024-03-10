package com.sunvalley.festivalFlowbe.controller.token;

import com.nimbusds.jose.JOSEException;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.VerificationCodeService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("festival-flow/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JWTTokenProviderService tokenProvider;

    private final VerificationCodeService verificationCodeService;

    private final EmailService emailService;

    private final CollaboratorService collaboratorService;


    @PostMapping("/login")
    public AuthEntity login(@RequestBody AuthEntity authEntity) {
        collaboratorService.createIfExistByEmail(authEntity.getEmail());
        int userId = collaboratorService.getIdByEmail(authEntity.getEmail());
        authEntity.setUserId(userId);
        log.info("userId: " + userId);
        verificationCodeService.createCode(userId);
        verificationCodeService.logCode(userId);
        String code = verificationCodeService.getCode(userId);
        authEntity.setEmailSended(emailService.sendCodeViaEmail(code, userId));
        if (!authEntity.isEmailSended()) {
            verificationCodeService.removeCode(userId);
            verificationCodeService.logCode(userId);
        }
        return authEntity;
    }

    @PostMapping("/login/confirm")
    public AuthEntity loginConfirm(@RequestBody AuthEntity authEntity) {
        if (verificationCodeService.isvalid( authEntity.getUserId(), authEntity.getCode())) {
            authEntity.setToken(tokenProvider.generateToken(authEntity.getUserId()));
            authEntity.setValid(true);
            return authEntity;
        } else {
            authEntity.setValid(false);
            return authEntity;
        }
    }

    @PostMapping("/validate")
    public AuthEntity validateToken(@RequestBody AuthEntity authEntity) {
        try {
            if (tokenProvider.validateToken(authEntity)) {
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
