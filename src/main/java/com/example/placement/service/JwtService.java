package com.example.placement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET = "this_is_the_backend_of_college_placement_portal";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(String username) {
        try {
            // Header
            Map<String, String> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            String encodedHeader = base64UrlEncode(objectMapper.writeValueAsString(header));

            // Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", username);
            payload.put("iat", System.currentTimeMillis() / 1000);
            payload.put("exp", (System.currentTimeMillis() + EXPIRATION_TIME) / 1000);
            String encodedPayload = base64UrlEncode(objectMapper.writeValueAsString(payload));

            // Signature
            String signatureInput = encodedHeader + "." + encodedPayload;
            String signature = hmacSha256(signatureInput, SECRET);

            return signatureInput + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    private String base64UrlEncode(String input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
