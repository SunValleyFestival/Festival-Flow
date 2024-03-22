package com.sunvalley.festivalFlowbe.service.utility;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerificationCodeService {

    private final Cache<Object, Object> cache;


    public VerificationCodeService() {
        this.cache = Caffeine.newBuilder()
                //expire after 2 minutes
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build();
    }


    public void createCode(int userId) {
        //random 6 number code
        String code = String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
        saveCode(userId, code);
    }

    public boolean isvalid(int userId, String code) {
        if (code == null) {
            return false;
        } else if (code.equals(cache.getIfPresent(userId))) {
            removeCode(userId);
            return true;
        } else {
            removeCode(userId);
            return false;

        }
    }


    //method only for test
//    public void logCode(int userId) {
//        log.info("code for user: " + userId + " is: " + cache.getIfPresent(userId));
//    }

    public String getCode(int userId) {
        return (String) cache.getIfPresent(userId);
    }


    public void removeCode(int userId) {
        cache.invalidate(userId);
    }

    private void saveCode(int userId, String code) {
        cache.put(userId, code);
    }
}
