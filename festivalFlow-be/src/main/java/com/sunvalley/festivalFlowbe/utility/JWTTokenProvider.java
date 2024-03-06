package com.sunvalley.festivalFlowbe.utility;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sunvalley.festivalFlowbe.config.ApplicationJwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
    private final ApplicationJwtConfig applicationJwtConfig;

    public String generateToken(Map<String, Object> claims) {

        final RSAPrivateKey privateKey = applicationJwtConfig.getPrivateKey();
        final JWSHeader header = new JWSHeader(applicationJwtConfig.getAlgorithm());
        final JWTClaimsSet claimsSet = buildJWTClaimsSet(claims);

        final SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            final JWSSigner signer = new RSASSASigner(privateKey);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("JWS object couldn't be signed", e);
        }

        return jwt.serialize();
    }

    private JWTClaimsSet buildJWTClaimsSet(Map<String, Object> claims) {
        final String issuer = applicationJwtConfig.getIssuer();
        final Instant issuedAt = Instant.now();
        final Instant expirationTime = issuedAt.plus(applicationJwtConfig.getExpirationTime());

        final JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expirationTime));

        claims.forEach(builder::claim);

        return builder.build();
    }

}