package com.sunvalley.festivalFlowbe.controller.utility;

import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.entity.utility.ConfigurationEntity;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.utility.ConfigurationService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.ExportService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/festival-flow/admin/")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AdminController {

    private final JWTTokenProviderService tokenProvider;
    private final ExportService exportService;
    private final EmailService emailService;
    private final ConfigurationService configurationService;

    @GetMapping("token")
    public AuthEntity login() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setToken(tokenProvider.generateTokenForAdmin());
        return authEntity;
    }

    @PostMapping("export")
    public String loginConfirm(EmailRequest emailRequest) throws IOException {
        emailService.sendExport(emailRequest.getTo(), exportService.export());
        return null;
    }

    @PostMapping("lock")
    public ResponseEntity<String> lock(ConfigurationEntity configurationEntity) {
        configurationService.updateByName(configurationEntity.getName(), false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("unlock")
    public ResponseEntity<String> unlock(ConfigurationEntity configurationEntity) {
        configurationService.updateByName(configurationEntity.getName(), true);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
