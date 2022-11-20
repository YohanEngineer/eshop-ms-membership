package com.episen.membership.security;

import com.episen.membership.setting.InfraSettings;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwTokenGenerator {

    private final Logger logger = LoggerFactory.getLogger(JwTokenGenerator.class);

    private JWSSigner signer;

    @Value("${secret.password:password}")
    private String PASSWORD;

    @PostConstruct
    public void init() {
        signer = new RSASSASigner(InfraSettings.keypairLoader(PASSWORD).getPrivate());
    }

    public String generateAccessToken(String subject, List<String> roles) {

        ZonedDateTime currentDate = ZonedDateTime.now();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(UUID.randomUUID().toString())
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .audience("web")
                .issuer("episen-membership")
                .issueTime(Date.from(currentDate.toInstant()))
                .expirationTime(Date.from(currentDate.plusHours(12).toInstant()))
                .jwtID(UUID.randomUUID().toString())
                .claim("ROLES", roles)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimSet);

        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        logger.info("Access token generated");
        return signedJWT.serialize();

    }

    public String generateRefreshToken(String subject, List<String> roles) {

        ZonedDateTime currentDate = ZonedDateTime.now();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(UUID.randomUUID().toString()) // TODO build private key id
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .audience("web")
                .issuer("episen-membership")
                .issueTime(Date.from(currentDate.toInstant()))
                .expirationTime(Date.from(currentDate.plusHours(14).toInstant()))
                .jwtID(UUID.randomUUID().toString())
                .claim("ROLES", roles)
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimSet);

        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        logger.info("Refresh token generated");
        return signedJWT.serialize();

    }
}



	

