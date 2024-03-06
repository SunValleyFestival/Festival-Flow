package com.sunvalley.festivalFlowbe.controller.token;

import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import com.sunvalley.festivalFlowbe.service.utility.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JWTTokenProviderService tokenProvider;

    private final VerificationCodeService verificationCodeService = new VerificationCodeService();

    @PostMapping("/login")
    public String login(@RequestBody Integer userId) {
        log.info("userId: " + userId);
        verificationCodeService.createCodeAndSend(Long.valueOf(userId));
        verificationCodeService.logCode(Long.valueOf(userId));
        //then send email

        return "code sent to email";
    }

    @PostMapping("/login/confirm")
    public String loginConfirm(@RequestBody Map<String, Object> claims) {
        if (verificationCodeService.isvalid(Long.valueOf((Integer) claims.get("userId")), (String) claims.get("code"))) {
            return tokenProvider.generateToken();
        } else {
            return "invalid code";
        }
    }


}
