package com.sunvalley.festivalFlowbe.controller.utility;

import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/festival-flow/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final JWTTokenProviderService tokenProvider;

    @CrossOrigin
    @GetMapping("admin/token")
    public AuthEntity login() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setToken(tokenProvider.generateTokenForAdmin());
        return authEntity;
    }

    @CrossOrigin
    @PostMapping("admin/export")
    public String loginConfirm() {
        return "not implmented yet";
    }


}
