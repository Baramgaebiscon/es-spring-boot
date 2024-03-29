package com.early.stage.demo.global.property;

import com.early.stage.demo.global.auth.enums.Algorithm;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperty {

    public static String algorithm;
    public static String secret;
    public static long accessValidTime;
    public static long refreshValidTime;
    public static Key secretKey;
    public static SignatureAlgorithm signatureAlgorithm;
    public static String prefix;

    @Value("#{environment['jwt.signature-algorithm']}")
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Value("#{environment['jwt.secret']}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("#{environment['jwt.valid-time.access-limit-milliseconds']}")
    public void setAccessValidTime(long accessValidTime) {
        this.accessValidTime = accessValidTime;
    }

    @Value("#{environment['jwt.valid-time.refresh-limit-milliseconds']}")
    public void setRefreshValidTime(long refreshValidTime) {
        this.refreshValidTime = refreshValidTime;
    }

    @Value("#{environment['jwt.header.prefix']}")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        signatureAlgorithm = Algorithm.valueOf(algorithm)
            .getSignatureAlgorithm();
    }


}

