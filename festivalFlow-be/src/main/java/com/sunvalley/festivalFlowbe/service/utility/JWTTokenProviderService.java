package com.sunvalley.festivalFlowbe.service.utility;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sunvalley.festivalFlowbe.config.ApplicationJwtConfig;
import com.sunvalley.festivalFlowbe.entity.utility.AuthEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTTokenProviderService {
    private final ApplicationJwtConfig applicationJwtConfig;

    public String generateToken(int userId) {

        final RSAPrivateKey privateKey = applicationJwtConfig.getPrivateKey();
        final JWSHeader header = new JWSHeader(applicationJwtConfig.getAlgorithm());

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.subject(String.valueOf(userId));
        builder.issuer("");
        builder.expirationTime(new Date(new Date().getTime() + 60 * 1000 * 60 * 24 * 7));
        builder.claim("role", "user");

        final JWTClaimsSet claim = builder.build();
        final SignedJWT jwt = new SignedJWT(header, claim);

        try {
            final JWSSigner signer = new RSASSASigner(privateKey);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("JWS object couldn't be signed", e);
        }

        return jwt.serialize();
    }

    public String generateTokenForAdmin() {
        final RSAPrivateKey privateKey = applicationJwtConfig.getPrivateKey();
        final JWSHeader header = new JWSHeader(applicationJwtConfig.getAlgorithm());

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.issuer("");
        builder.expirationTime(new Date(new Date().getTime() + 60 * 1000));
        builder.claim("role", "admin");

        final JWTClaimsSet claim = builder.build();
        final SignedJWT jwt = new SignedJWT(header, claim);

        try {
            final JWSSigner signer = new RSASSASigner(privateKey);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("JWS object couldn't be signed", e);
        }

        return jwt.serialize();
    }

    public boolean validateToken(AuthEntity authEntity) throws JOSEException, ParseException {
        SignedJWT parsedJWT = SignedJWT.parse(authEntity.getToken());
        RSAPublicKey publicKey = applicationJwtConfig.getPublicKey();
        JWTClaimsSet claims = parsedJWT.getJWTClaimsSet();

        if (!parsedJWT.verify(new RSASSAVerifier(publicKey)) &&
                !claims.getSubject().equals(String.valueOf(authEntity.getUserId()))) {
            return false;
        }

        return !claims.getExpirationTime().before(new Date());
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