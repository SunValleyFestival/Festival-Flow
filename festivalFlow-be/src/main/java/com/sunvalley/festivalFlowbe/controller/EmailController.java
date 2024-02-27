package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.utility.EmailService;
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
  public void sendEmail() {
    log.debug("Sending email...");
    try {
      emailService.sendEmail("aramis.grata@bluewin.ch", "Subject", "Hello, this is the body of the email!");
      log.debug("Email sent successfully!");
    } catch (MessagingException e) {
      log.error("Error sending email: " + e.getMessage());
    }
  }
}