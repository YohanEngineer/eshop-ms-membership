package com.episen.membership.service;

import com.episen.membership.security.JwTokenGenerator;
import com.episen.membership.setting.InfraSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshServiceImplementation implements RefreshService {

    private final JwTokenGenerator jwTokenGenerator;

    @Override
    public  Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Map<String, String> tokens = new HashMap<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                SignedJWT signedJWT;
                signedJWT = SignedJWT.parse(refresh_token);
                if (!isSignatureValid(signedJWT)) {
                    throw new RuntimeException("Token invalid");
                }
                String username = signedJWT.getJWTClaimsSet().getSubject();
                List<String> roles = (List<String>) signedJWT.getJWTClaimsSet().getClaim("ROLES");
                roles.forEach((role) -> {
                    System.out.print(role + " ");
                });
                log.info("Generating new tokens after refresh request...");
                String new_access_token = jwTokenGenerator.generateAccessToken(username, roles);
                String new_refresh_token = jwTokenGenerator.generateRefreshToken(username, roles);
                response.setHeader("ACCESS_TOKEN", new_access_token);
                response.setHeader("REFRESH_TOKEN", refresh_token);
                tokens.put("ACCESS_TOKEN", new_access_token);
                tokens.put("REFRESH_TOKEN", new_refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        return tokens;
    }

    public static boolean isSignatureValid(SignedJWT signedJWT) {
        try {
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) InfraSettings.keypairLoader("password").getPublic());
            return signedJWT.verify(verifier);
        } catch (JOSEException e) {
            return false;
        }
    }

}
