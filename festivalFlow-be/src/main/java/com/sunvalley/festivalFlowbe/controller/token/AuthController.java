package com.sunvalley.festivalFlowbe.controller.token;

import com.sunvalley.festivalFlowbe.utility.JWTTokenProvider;
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
    private final JWTTokenProvider tokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> claims) {
        return tokenProvider.generateToken(claims);
    }

}
