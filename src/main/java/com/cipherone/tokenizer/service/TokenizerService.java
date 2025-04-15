package com.cipherone.tokenizer.service;

import com.cipherone.tokenizer.entity.TokenMapping;
import com.cipherone.tokenizer.model.TokenRequest;
import com.cipherone.tokenizer.model.TokenResponse;
import com.cipherone.tokenizer.repository.TokenMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TokenizerService {

    @Autowired
    private TokenMappingRepository tokenRepo;

    public TokenResponse generateTokens(TokenRequest request) {
        Map<String, String> tokenMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        for (Map.Entry<String, String> entry : request.getPiiMap().entrySet()) {
            String label = entry.getKey();         // e.g., "name"
            String piiValue = entry.getValue();    // e.g., "sachin"

            Optional<TokenMapping> existing = tokenRepo.findByPiiValue(piiValue);

            String token = existing
                    .filter(mapping -> mapping.getExpiresAt().isAfter(now))
                    .map(TokenMapping::getToken)
                    .orElseGet(() -> {
                        String newToken = UUID.randomUUID().toString();
                        TokenMapping mapping = new TokenMapping();
                        mapping.setPiiValue(piiValue);
                        mapping.setToken(newToken);
                        mapping.setExpiresAt(now.plusDays(60)); // change to 86400 for 24h
                        tokenRepo.save(mapping);
                        return newToken;
                    });

            tokenMap.put(label, token);
        }

        TokenResponse response = new TokenResponse();
        response.setTokenMap(tokenMap);
        return response;
    }


    public String resolveToken(String token) {
        return tokenRepo.findByToken(token).map(TokenMapping::getPiiValue).orElse(null);
    }

    public TokenResponse refreshTokens(TokenRequest request) {
        Map<String, String> tokenMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        for (Map.Entry<String, String> entry : request.getPiiMap().entrySet()) {
            String label = entry.getKey();         // e.g., "name"
            String piiValue = entry.getValue();    // e.g., "sachin"
            String newToken = UUID.randomUUID().toString();

            Optional<TokenMapping> existing = tokenRepo.findByPiiValue(piiValue);

            TokenMapping mapping = existing.orElseGet(TokenMapping::new);
            mapping.setPiiValue(piiValue);
            mapping.setToken(newToken);
            mapping.setExpiresAt(now.plusHours(24));
            tokenRepo.save(mapping);

            tokenMap.put(label, newToken);
        }

        TokenResponse response = new TokenResponse();
        response.setTokenMap(tokenMap);
        return response;
    }

}
