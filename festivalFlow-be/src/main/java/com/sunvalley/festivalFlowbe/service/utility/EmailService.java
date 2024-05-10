package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
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

import java.util.List;

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
    emailRequest.setMessage("Il tuo codice di verifica è: " + code + "<br><br> Il team SVF");
    return sendEmail(emailRequest);

  }

  public void sendReminder(CollaboratorEntity collaboratorEntity) {
    EmailRequest emailRequest = new EmailRequest();
    //attenzione cambiare in produzione
    emailRequest.setTo(collaboratorEntity.getEmail());
    emailRequest.setSubject("Ricordati del tuo turno!");
    StringBuilder message= new StringBuilder();

    List<ShiftEntity> shiftEntities = shiftService.getShiftByCollaboratorIdAndShiftAccepted(collaboratorEntity.getId());
    message.append("I tuoi turni: <br>");
    for (ShiftEntity shift : shiftEntities){
      message.append("<br>")
              .append("Nome: ").append(shift.getName()).append("<br>")
              .append("Postazione: ").append(shift.getLocation().getName()).append("<br>")
              .append("Giorno: ").append(shift.getLocation().getDay().getName()).append("<br>")
              .append("Inizio: ").append(shift.getStartTime()).append("<br>")
              .append("Fine: ").append(shift.getEndTime()).append("<br>");
    }

    message.append( "<br><br>Ognuno di noi qui contribuisce volontariamente per il Festival e per l'Associazione Asilo Beach: è semplicemente parte della nostra cultura e della nostra identità. Per far stare in piedi il tutto dobbiamo avere RISPETTO ed evitare comportamenti come:<br>")
            .append("- Presentarsi in ritardo<br>")
            .append("- Andare in backstage<br>")
            .append("- Ubriacarsi durante il turno<br>")
            .append("- Dare da bere gratis<br>")
            .append("- Entrare nei bar quando non si è di turno");

    message.append(" <br><br> Non dimenticate di presentarvi 20min in anticipo al vostro turno!  <br> Per domande scrivere a ")
            .append("Jail(<a href=\"tel:0796588917\">079 658 89 17</a>) o ")
            .append("Bryan (<a href=\"tel:0796510156\">079 651 01 56</a>).");

    message.append(" <br><br> Grazie per la tua collaborazione! <br> Il team SVF!");
    emailRequest.setMessage(message.toString());
     log.info("sending email... to" + emailRequest.getTo());
     log.info(emailRequest.getMessage());
    sendEmail(emailRequest);

  }

  public void sendNotificationViaEmail(int userId, Status status, int shiftId) {
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setTo(collaboratorService.getEmailById(userId));
    ShiftEntity shiftEntity;
    shiftEntity = shiftService.getById(shiftId);
    var message = "";

    switch (status) {
      case ACCEPTED:
        emailRequest.setSubject("Turno accettato!");
        message = "Il tuo turno: " + shiftEntity.getName() + " alla postazione: " + shiftEntity.getLocation().getName() + " al: " + shiftEntity.getLocation().getDay().getName()
            + " è stato accettato <br> Il tuo turno inizia alle: " + shiftEntity.getStartTime() + " e finisce alle: " + shiftEntity.getEndTime() + " <br><br>"
            + collaboratorService.getDataForEmail(userId);

        message +=
            "<br><br>Ognuno di noi qui contribuisce volontariamente per il Festival e per l'Associazione Asilo Beach: è semplicemente parte della nostra cultura e della nostra identità. Per "
                + "far stare in piedi il tutto dobbiamo avere RISPETTO ed evitare comportamenti come:<br>";
        message +=
            "- Presentarsi in ritardo<br>"
                + "- Andare in backstage<br>"
                + "- Presentarsi ubriachi al turno<br>"
                + "- Ubriacarsi durante il turno<br>"
                + "- Dare da bere gratis<br>"
                + "- Entrare nei bar quando non si è di turno";

        message +=
            " <br><br> Non dimenticate di presentarvi 20min in anticipo al vostro turno! Per domande scrivere a "
                + "Jail(<a href=\"tel:0796588917\">079 658 89 17</a>) o "
                + "Bryan (<a href=\"tel:0796510156\">079 651 01 56</a>).";

        break;
      case REJECTED:
        emailRequest.setSubject("Turno rifiutato!");
        message =
            "Turno rifiutato!<br><br>Turno: <br>Nome: " + shiftEntity.getName() + "<br>Postazione: " + shiftEntity.getLocation().getName() + "<br>Giorno: " + shiftEntity.getLocation().getDay()
                .getName()
                + "<br>Inizio: " + shiftEntity.getStartTime() + "<br>Termine: " + shiftService.getById(shiftId).getEndTime() + " <br><br>"
            + collaboratorService.getDataForEmail(userId);
        break;
      case PENDING:
        emailRequest.setSubject("Turno in attesa!");
        message = "Il tuo turno: " + shiftEntity.getName() + " della postazione: " + shiftEntity.getLocation().getName() + " di: " + shiftEntity.getLocation().getDay().getName()
            + " è in attesa di approvazione \n Il tuo turno inizia alle: " + shiftEntity.getStartTime() + " e finisce alle: " + shiftService.getById(shiftId).getEndTime() + " <br><br>"
            + collaboratorService.getDataForEmail(userId);
        break;
      default:
        log.error("Invalid status");
        return;
    }
    message += " <br><br> Grazie per la tua collaborazione! <br> Il team SVF!";

    emailRequest.setMessage(message);

    sendEmail(emailRequest);
  }

  public void sendCollabortorInfo(int userId) {
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setTo(collaboratorService.getEmailById(userId));
    emailRequest.setSubject("Info personali");
    emailRequest.setMessage(collaboratorService.getDataForEmail(userId) + " <br> Il team SVF!");
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