package com.sunvalley.festivalFlowbe.config;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.service.CollaboratorService;
import com.sunvalley.festivalFlowbe.service.utility.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class CronJobService {

    private final EmailService emailService;
    private final CollaboratorService collaboratorService;

    @Scheduled(cron = "0 14 13 10 5 *")
    public void cronJobprova() {
        List<CollaboratorEntity> collaboratorEntityList = collaboratorService.findCollaboratorEntitiesWhereIsPopulatedAndAssociationAccepted();
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // Crea un thread pool con un solo thread

        AtomicInteger collaboratoriInviati = new AtomicInteger();
        log.info("Invio email a " +collaboratorEntityList.size());
        for (CollaboratorEntity collaboratorEntity : collaboratorEntityList) {

            executorService.submit(() -> {
                log.info("Email a " + collaboratorEntity.getEmail()+ " n " + collaboratoriInviati.get() + " di " + collaboratorEntityList.size());

                emailService.sendReminder(collaboratorEntity);
                collaboratoriInviati.getAndIncrement();

                // Pausa di 1 secondo dopo ogni email
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Pausa di 1 ora dopo ogni 300 email
                if (collaboratoriInviati.get() % 300 == 0) {
                    try {
                        log.info("300 email inviate! pausa di 1h");
                        Thread.sleep(3600000); // 1 ora in millisecondi

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Aspetta la terminazione di tutte le attività inviate al thread pool
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
