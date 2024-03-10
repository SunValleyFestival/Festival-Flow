package com.sunvalley.festivalFlowbe.controller.utility;

import com.nimbusds.jose.JOSEException;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import com.sunvalley.festivalFlowbe.service.utility.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/festival-flow/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JWTTokenProviderService tokenProvider;

    private final VerificationCodeService verificationCodeService;

    private final EmailService emailService;

    private final CollaboratorService collaboratorService;


    @CrossOrigin
    @PostMapping("/login")
    public boolean login(@RequestBody AuthEntity authEntity) {
        collaboratorService.createIfExistByEmail(authEntity.getEmail());
        int userId = collaboratorService.getIdByEmail(authEntity.getEmail());
        log.info("userId: " + authEntity.getUserId());
        verificationCodeService.createCode(userId);
        verificationCodeService.logCode(userId);
        String code = verificationCodeService.getCode(userId);
        boolean emailSend = emailService.sendCodeViaEmail(code, userId);
        if (!emailSend) {
            verificationCodeService.removeCode(userId);
            verificationCodeService.logCode(userId);
        }
        return emailSend;
    }

    @CrossOrigin
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
            authEntity.setValid(tokenProvider.validateToken(authEntity));
        } catch (JOSEException | ParseException e) {
            authEntity.setValid(false);
        }
        return authEntity;
    }

}
