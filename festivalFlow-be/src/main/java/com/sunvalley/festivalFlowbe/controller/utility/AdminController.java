package com.sunvalley.festivalFlowbe.controller.utility;

import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.ExportService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/festival-flow/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final JWTTokenProviderService tokenProvider;
    private final ExportService exportService;
    private final EmailService emailService;

    @CrossOrigin
    @GetMapping("token")
    public AuthEntity login() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setToken(tokenProvider.generateTokenForAdmin());
        return authEntity;
    }

    @CrossOrigin
    @PostMapping("export")
    public String loginConfirm(EmailRequest emailRequest) throws IOException, InvalidFormatException {
        emailService.sendExport(emailRequest.getTo(), exportService.export());
        return null;
    }


}
