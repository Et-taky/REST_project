package com.project1.project1.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class GenerateEtag {

    private static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .findAndRegisterModules();

    /**
     * Generate ETag from object using MD5.
     * Excludes null fields and ensures consistent serialization.
     */
    public static String generate(Object object) {
        try {
            String json = mapper.writeValueAsString(object);

            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ETag", e);
        }
    }
}
