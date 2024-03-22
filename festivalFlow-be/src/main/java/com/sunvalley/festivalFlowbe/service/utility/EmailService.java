package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.entity.utility.Attachment;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final CollaboratorService collaboratorService;
    private final ShiftService shiftService;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderAddress;
    @Value("${spring.mail.properties.enabled}")
    private boolean enabled;
    @Value("${spring.mail.properties.test-receiver}")
    private String testReceiver;

    public EmailService(JavaMailSender mailSender, CollaboratorService collaboratorService, ShiftService shiftService) {
        this.mailSender = mailSender;
        this.collaboratorService = collaboratorService;
        this.shiftService = shiftService;
    }

    public boolean sendEmail(EmailRequest emailRequest) {
        log.info("Sending email to <{}> with subject<{}>...", emailRequest.getTo(), emailRequest.getSubject());

        if (!enabled) {
            log.info("Send email disabled, check configuration");
            return false;
        }

//        addTestReceiverIfConfigured(emailRequest);
        return sendMimeMessage(emailRequest);
    }

    public boolean sendExport(String email, Attachment attachment) {

        if (!enabled) {
            log.info("Send email disabled, check configuration");
            return false;
        }

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject("Export");
        emailRequest.setMessage("Export");

//        addTestReceiverIfConfigured(emailRequest);
        return sendMimeMessageExport(emailRequest, attachment);
    }

    public boolean sendCodeViaEmail(String code, int userId) {
        log.info("Sending code to user <{}>...", userId);
        var emailRequest = new EmailRequest();
        String email = collaboratorService.getEmailById(userId);
        if (email == null || code.isEmpty()) {
            return false;
        }
        emailRequest.setTo(email);
        emailRequest.setSubject("Codice di verifica");

        //add message with link
        emailRequest.setMessage("Il tuo codice di verifica é: " + code);
        return sendEmail(emailRequest);

    }

    public void sendNotificationViaEmail(int userId, Status status, int shiftId) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(collaboratorService.getEmailById(userId));
        // add switch for Status
        switch (status) {
            case ACCEPTED:
                emailRequest.setSubject("Turno accettato!");
                emailRequest.setMessage("Il tuo turno: " + shiftService.getById(shiftId).getName() + " è stato accettato <br> Il tuo turno inizia alle: " + shiftService.getById(shiftId).getStartTime() + " e finisce alle: " + shiftService.getById(shiftId).getEndTime() + "\n localhost:4200/collaborator \n\n Grazie per la tua collaborazione \n Il team di SVF");
                break;
            case REJECTED:
                emailRequest.setSubject("Turno rifiutato!");
                emailRequest.setMessage("Il tuo turno: " + shiftService.getById(shiftId).getName() + " è stato rifiutato \n Il tuo turno sarebbe iniziato alle: " + shiftService.getById(shiftId).getStartTime() + " e finito alle: " + shiftService.getById(shiftId).getEndTime() + "\n localhost:4200/collaborator \n\n Grazie per la tua collaborazione \n Il team di SVF");
                break;
            case PENDING:
                emailRequest.setSubject("Turno in attesa!");
                emailRequest.setMessage("Il tuo turno: " + shiftService.getById(shiftId).getName() + " è in attesa di approvazione \n Il tuo turno inizia alle: " + shiftService.getById(shiftId).getStartTime() + " e finisce alle: " + shiftService.getById(shiftId).getEndTime() + "\n localhost:4200/collaborator \n\n Grazie per la tua collaborazione \n Il team di SVF");
                break;
            default:
                log.error("Invalid status");
                return;
        }
        log.info("Sending notification to <{}> with subject<{}>...", emailRequest.getTo(), emailRequest.getSubject());
        sendEmail(emailRequest);
    }

    private boolean sendMimeMessage(EmailRequest emailRequest) {
        var mimeMessage = mailSender.createMimeMessage();
        try {
            var helper = getMimeMessageHelper(emailRequest, mimeMessage);
            //addAttachments(helper, emailRequest.getAttachments());

            mailSender.send(mimeMessage);
            log.info("Email sent");
            return true;

        } catch (MessagingException e) {
            log.error("Error sending email", e);
            return false;
        }
    }

    private boolean sendMimeMessageExport(EmailRequest emailRequest, Attachment attachment) {
        var mimeMessage = mailSender.createMimeMessage();
        try {
            var helper = getMimeMessageHelper(emailRequest, mimeMessage);

            addAttachments(helper, attachment);

            mailSender.send(mimeMessage);
            log.info("Email sent");
            return true;

        } catch (MessagingException e) {
            log.error("Error sending email", e);
            return false;
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

    private void addAttachments(MimeMessageHelper helper, Attachment attachment) {
        addAttachment(helper, attachment);
    }

    private void addAttachment(MimeMessageHelper helper, Attachment attachment) {
        try {
            helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
        } catch (MessagingException e) {
            log.error("Error adding attachment", e);
        }
    }
}