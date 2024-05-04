package com.sunvalley.festivalFlowbe.scheduler;

import com.sunvalley.festivalFlowbe.entity.utility.EmailRequest;
import com.sunvalley.festivalFlowbe.service.AssociationService;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.LocationService;
import com.sunvalley.festivalFlowbe.service.ShiftService;
import com.sunvalley.festivalFlowbe.service.utility.ConfigurationService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

  private final EmailService emailService;
  private final AssociationService associationService;
  private final ShiftService shiftService;
  private final CollaboratorService collaboratorService;
  private final ConfigurationService configurationService;
  private final LocationService locationService;

  private final ArrayList<EmailRequest> emailRequestsQueue = new ArrayList<>();

  @Scheduled(cron = "0 0 * * *")
  public void sendEmail() {
    var email = configurationService.getByName("reminderScheduler");
    if (email.equals("1")) {
      log.info("Scheduler started");

      var associations = associationService.getAll();
      EmailRequest emailRequest = new EmailRequest();

      for (com.sunvalley.festivalFlowbe.entity.AssociationEntity association : associations) {
        var shiftId = association.getId().getShiftId();
        var collaboratorId = association.getId().getCollaboratorId();

        var shift = shiftService.getById(shiftId);
        var collaborator = collaboratorService.getById(collaboratorId);
        var location = locationService.getByShiftId(shiftId);

        Date currentDate = new Date();
        Date shiftDate;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.MONTH, 5);

        if (location.getName().toLowerCase().contains("ve")) {
          cal.set(Calendar.DAY_OF_MONTH, 17);
        } else if (location.getName().toLowerCase().contains("sa")) {
          cal.set(Calendar.DAY_OF_MONTH, 18);
        } else {
          cal.set(Calendar.DAY_OF_MONTH, 19);
        }

        shiftDate = cal.getTime();

        // if shift date is one week away from current date
        if (shiftDate.getTime() - currentDate.getTime() == 604800000) {
          emailRequest.setTo(collaborator.getEmail());
          emailRequest.setSubject("Reminder SVF");
          emailRequest.setMessage("You have a shift in one week");
          emailRequestsQueue.add(emailRequest);
        }

        // if shift date is today
        if (shiftDate.getTime() - currentDate.getTime() == 0) {
          emailRequest.setTo(collaborator.getEmail());
          emailRequest.setSubject("Reminder SVF");
          emailRequest.setMessage("You have a shift today");
          emailRequestsQueue.add(emailRequest);
        }
      }

      log.info("sending notifications...");
      for (EmailRequest emailRequest1 : emailRequestsQueue) {
        try {
          int waitTime = Integer.parseInt(configurationService.getByName("emailWaitTime"));
          Thread.sleep(waitTime);
          emailService.sendEmail(emailRequest1);
        } catch (InterruptedException e) {
          log.error("Error sending notification", e);
        }
      }

      emailRequestsQueue.clear();
      log.info("Scheduler finished");
    }
  }
}
