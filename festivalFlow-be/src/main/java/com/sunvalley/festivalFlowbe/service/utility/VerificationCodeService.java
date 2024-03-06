package com.sunvalley.festivalFlowbe.service.utility;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerificationCodeService {

    private final Cache<Object, Object> cache;

    @Autowired
    private EmailService emailService;

    @Autowired
    public VerificationCodeService(EmailService emailService) {
        this.emailService = emailService;
        this.cache = Caffeine.newBuilder()
                //expire after 2 minutes
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build();
    }


    public void createCodeAndSend(Long userId) {
        //random 6 number code
        String code = String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
        saveCode(userId, code);
        emailService.sendEmail(Objects.requireNonNull(cache.getIfPresent(userId)).toString());
    }

    public boolean isvalid(Long userId, String code) {
        if (code == null || userId == null) {
            return false;
        } else if (code.equals(cache.getIfPresent(userId))) {
            removeCode(userId);
            return true;
        } else {
            return false;

        }
    }


    //method only for test
    public void logCode(Long userId) {
        log.info("code for user: " + userId + " is: " + cache.getIfPresent(userId));
    }


    private void removeCode(Long userId) {
        cache.invalidate(userId);
    }

    private void saveCode(Long userId, String code) {
        cache.put(userId, code);
    }
}
