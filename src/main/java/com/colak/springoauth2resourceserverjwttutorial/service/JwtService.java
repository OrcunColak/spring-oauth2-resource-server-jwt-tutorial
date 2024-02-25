package com.colak.springoauth2resourceserverjwttutorial.service;

import com.colak.springoauth2resourceserverjwttutorial.config.AppJwtProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final AppJwtProperties appJwtProperties;

    public String generateJWT(Map<String, Object> claims) {
        SecretKey key = appJwtProperties.getKey();
        JWSAlgorithm algorithm = appJwtProperties.getAlgorithm();

        JWSHeader header = new JWSHeader(algorithm);
        JWTClaimsSet claimsSet = buildClaimsSet(claims);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            var signer = new MACSigner(key);
            jwt.sign(signer);
        } catch (JOSEException exception) {
            throw new RuntimeException("Unable to generate JWT", exception);
        }

        return jwt.serialize();
    }

    private JWTClaimsSet buildClaimsSet(Map<String, Object> claims) {
        String issuer = appJwtProperties.getIssuer();
        Instant issuedAt = Instant.now();
        Instant expirationTime = issuedAt.plus(appJwtProperties.getExpiresIn());

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expirationTime));

        claims.forEach(builder::claim);

        return builder.build();
    }

}
