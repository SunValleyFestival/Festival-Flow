package com.sunvalley.festivalFlowbe.controller;

import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    email.setTo("joekueng05@gmail.com");
    email.setSubject("Test");
    email.setMessage("Test message");

//    emailService.sendEmail(email);
    log.debug("Email sent successfully!");
    return "Email sent successfully!";
  }
}