package com.sunvalley.festivalFlowbe.config;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
public class ApplicationJwtConfig {


    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    private JWSAlgorithm algorithm;
    private String issuer;

    private Duration expirationTime;

}