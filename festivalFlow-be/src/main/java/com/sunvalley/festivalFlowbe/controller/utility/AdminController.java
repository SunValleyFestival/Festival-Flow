package com.sunvalley.festivalFlowbe.controller.utility;

import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.utility.ConfigurationService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import com.sunvalley.festivalFlowbe.service.utility.ExportService;
import com.sunvalley.festivalFlowbe.service.utility.JWTTokenProviderService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public String export(@RequestBody EmailRequest emailRequest) throws IOException {
        log.info("Exporting data and sending email to <{}>...", emailRequest.getTo());
        emailService.sendExport(emailRequest.getTo(), exportService.export());
        return null;
    }

    @GetMapping("lock/get")
    public ResponseEntity<Boolean> isLocked() {
        return new ResponseEntity<>(configurationService.getByName("lock"), HttpStatus.OK);
    }

    @PostMapping("lock")
    public ResponseEntity<Boolean> lock(@RequestBody boolean lock) {
        configurationService.updateByName("lock", lock);
        boolean isLocked = configurationService.getByName("lock");
        return new ResponseEntity<>(isLocked, HttpStatus.OK);
    }
}
