package com.sunvalley.festivalFlowbe.controller.utility;

import com.nimbusds.jose.JOSEException;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import com.sunvalley.festivalFlowbe.service.utility.VerificationCodeService;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival-flow/user/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JWTTokenProviderService tokenProvider;

    private final VerificationCodeService verificationCodeService;

    private final EmailService emailService;

    private final CollaboratorService collaboratorService;

    @CrossOrigin
    @PostMapping("login/mail")
    public AuthEntity mailLogin(@RequestBody AuthEntity authEntity) {
        authEntity.setValid(collaboratorService.existsByEmail(authEntity.getEmail()));
        return authEntity;
    }

    @CrossOrigin
    @PostMapping("login")
    public AuthEntity login(@RequestBody AuthEntity authEntity) {
        collaboratorService.createIfExistByEmail(authEntity);
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

    @CrossOrigin
    @PostMapping("login/confirm")
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

    @PostMapping("validate")
    public AuthEntity validateToken(@RequestBody AuthEntity authEntity) {
        try {
            authEntity.setValid(tokenProvider.validateToken(authEntity));
        } catch (JOSEException | ParseException e) {
            authEntity.setValid(false);
        }
        return authEntity;
    }

}
