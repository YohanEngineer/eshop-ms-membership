package com.episen.membership.setting;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class InfraSettings {

    public static KeyPair keypairLoader(String password) {



        try (InputStream is = new ClassPathResource("keys/server.p12").getInputStream()) {

            KeyStore kstore = KeyStore.getInstance("PKCS12");
            kstore.load(is, password.toCharArray());

            Key key = kstore.getKey("episen", password.toCharArray());

            Certificate certificate = kstore.getCertificate("episen");

            return new KeyPair(certificate.getPublicKey(), (PrivateKey) key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
