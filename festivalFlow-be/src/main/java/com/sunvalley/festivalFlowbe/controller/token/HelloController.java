package com.sunvalley.festivalFlowbe.controller.token;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello(@AuthenticationPrincipal final Jwt jwt) {
        final Map<String, Object> claims = jwt.getClaims();
        return "Hello Received with from JWT Token With Claims: %s".formatted(claims.toString());

    }
}
