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

    public String generateToken() {

        final RSAPrivateKey privateKey = applicationJwtConfig.getPrivateKey();
        final JWSHeader header = new JWSHeader(applicationJwtConfig.getAlgorithm());
        final JWTClaimsSet claim = new JWTClaimsSet.Builder()
                .subject("tipo")
                .issuer("https://c2id.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .claim("role", "user")
                .build();
//        final JWTClaimsSet claimsSet = buildJWTClaimsSet(claim);

        final SignedJWT jwt = new SignedJWT(header, claim);

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