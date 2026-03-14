package com.gateway.ms.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

@Configuration
public class JwtConfig {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        SecretKey key = new SecretKeySpec(
                SECRET.getBytes(),
                "HmacSHA256"
        );

        return NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .build();
    }
}