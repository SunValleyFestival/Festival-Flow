package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.entity.Status;
import com.sunvalley.festivalFlowbe.entity.utility.Attachment;
import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.DayService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final CollaboratorService collaboratorService;
    private final ShiftService shiftService;
    private final DayService dayService;
    private final LocationService locationService;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderAddress;
    @Value("${spring.mail.properties.enabled}")
    private boolean enabled;
    @Value("${spring.mail.properties.test-receiver}")
    private String testReceiver;

    public boolean sendEmail(EmailRequest emailRequest) {
        log.info("Sending email to <{}> with subject<{}>...", emailRequest.getTo(), emailRequest.getSubject());

        if (!enabled) {
            log.info("Send email disabled, check configuration");
            return false;
        }

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
        emailRequest.setMessage("Il tuo codice di verifica é: " + code + "<br><br> Il team di SVF");
        return sendEmail(emailRequest);

    }

    public void sendNotificationViaEmail(int userId, Status status, int shiftId) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(collaboratorService.getEmailById(userId));
        ShiftEntity shiftEntity;
        switch (status) {
            case ACCEPTED:
                shiftEntity = shiftService.getById(shiftId);
                emailRequest.setSubject("Turno accettato!");
                emailRequest.setMessage("Il tuo turno: " + shiftEntity.getName() + " alla postazione: " + shiftEntity.getLocation().getName() + " al: " + shiftEntity.getLocation().getDay().getName() + " è stato accettato <br> Il tuo turno inizia alle: " + shiftEntity.getStartTime() + " e finisce alle: " + shiftEntity.getEndTime() + " <br><br>" + collaboratorService.getDataForEmail(userId) + " <br><br> Grazie per la tua collaborazione! <br> Il team di SVF!");
                break;
            case REJECTED:
                shiftEntity = shiftService.getById(shiftId);
                emailRequest.setSubject("Turno rifiutato!");
                emailRequest.setMessage("Il tuo turno: " + shiftEntity.getName() + " alla postazione: " + shiftEntity.getLocation().getName() + " al: " + shiftEntity.getLocation().getDay().getName() + "è stato rifiutato <br> Il tuo turno sarebbe iniziato alle: " + shiftEntity.getStartTime() + " e finito alle: " + shiftService.getById(shiftId).getEndTime() + " <br><br>" + collaboratorService.getDataForEmail(userId) + "<br><br> Grazie per la tua collaborazione <br> Il team di SVF");
                break;
            case PENDING:
                shiftEntity = shiftService.getById(shiftId);
                emailRequest.setSubject("Turno in attesa!");
                emailRequest.setMessage("Il tuo turno: " + shiftEntity.getName() + " alla postazione: " + shiftEntity.getLocation().getName() + " al: " +shiftEntity.getLocation().getDay().getName() + " è in attesa di approvazione \n Il tuo turno inizia alle: " + shiftEntity.getStartTime() + " e finisce alle: " + shiftService.getById(shiftId).getEndTime()  + " <br><br>" + collaboratorService.getDataForEmail(userId) +" <br><br>  Grazie per la tua collaborazione <br> Il team di SVF");
                break;
            default:
                log.error("Invalid status");
                return;
        }
        sendEmail(emailRequest);
    }

    public void sendCollabortorInfo(int userId) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(collaboratorService.getEmailById(userId));
        emailRequest.setSubject("Info personali");
        emailRequest.setMessage(collaboratorService.getDataForEmail(userId)+" <br> Il team di SVF!");
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