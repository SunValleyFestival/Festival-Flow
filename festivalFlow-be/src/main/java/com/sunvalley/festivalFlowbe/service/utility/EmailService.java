package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.utility.Attachment;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderAddress;
    @Value("${spring.mail.properties.enabled}")
    private boolean enabled;
    @Value("${spring.mail.properties.test-receiver}")
    private String testReceiver;


    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String code) {


        log.debug("Sending email...");

        var email = new EmailRequest();
        email.setTo(testReceiver);
        email.setSubject("Test code");
        email.setMessage("Your verification code is: " + code);

        log.debug("Email sent successfully!");

        if (!enabled) {
            log.info("Send email disabled, check configuration");
            return;
        }

        addTestReceiverIfConfigured(email);
        sendMimeMessage(email);
    }

    private void sendMimeMessage(EmailRequest emailRequest) {
        var mimeMessage = mailSender.createMimeMessage();
        try {
            var helper = getMimeMessageHelper(emailRequest, mimeMessage);
            //addAttachments(helper, emailRequest.getAttachments());

            mailSender.send(mimeMessage);
            log.info("Email sent");

        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }

    private MimeMessageHelper getMimeMessageHelper(EmailRequest emailRequest, MimeMessage mimeMessage) throws MessagingException {
        var helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getMessage(), true);
        helper.setFrom(senderAddress);

        return helper;
    }

    private void addTestReceiverIfConfigured(EmailRequest emailRequest) {
        if (StringUtils.isNotEmpty(testReceiver)) {
            log.info("Test receiver configured, email will be forwarded to this address: <{}>", testReceiver);
            emailRequest.setTo(testReceiver);
        }
    }

    private void addAttachments(MimeMessageHelper helper, List<Attachment> attachments) {
        if (!attachments.isEmpty()) {
            attachments.forEach(attachment -> addAttachment(helper, attachment));
        }
    }

    private void addAttachment(MimeMessageHelper helper, Attachment attachment) {
        try {
            helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
        } catch (MessagingException e) {
            log.error("Error adding attachment", e);
        }
    }
}