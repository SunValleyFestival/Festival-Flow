package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.utility.EmailService;
import com.sunvalley.festivalFlowbe.utility.notification.EmailRequest;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/festival-flow/email")
public class EmailController {

  @Autowired
  private EmailService emailService;

  @PostMapping("/send-email")
  public String sendEmail() {
    log.debug("Sending email...");
    var email = new EmailRequest();
    email.setTo("aramis.grata@bluewin.ch");
    email.setSubject("Test");
    email.setMessage("Test message");

    emailService.sendEmail(email);
    log.debug("Email sent successfully!");
    return "Email sent successfully!";
  }
}