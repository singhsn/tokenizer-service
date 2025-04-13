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

        for (String pii : request.getPiiValues()) {
            Optional<TokenMapping> existing = tokenRepo.findByPiiValue(pii);

            String token = existing
                    .filter(mapping -> mapping.getExpiresAt().isAfter(now)) // not expired
                    .map(TokenMapping::getToken)
                    .orElseGet(() -> {
                        String newToken = UUID.randomUUID().toString();
                        TokenMapping mapping = new TokenMapping();
                        mapping.setPiiValue(pii);
                        mapping.setToken(newToken);
                        mapping.setExpiresAt(now.plusSeconds(60)); // 24h validity
                        tokenRepo.save(mapping);
                        return newToken;
                    });

            tokenMap.put(pii, token);
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

        for (String pii : request.getPiiValues()) {
            Optional<TokenMapping> existing = tokenRepo.findByPiiValue(pii);
            String newToken = UUID.randomUUID().toString();

            if (existing.isPresent()) {
                TokenMapping mapping = existing.get();
                mapping.setToken(newToken);
                mapping.setExpiresAt(now.plusHours(24));
                tokenRepo.save(mapping);
            } else {
                TokenMapping mapping = new TokenMapping();
                mapping.setPiiValue(pii);
                mapping.setToken(newToken);
                mapping.setExpiresAt(now.plusHours(24));
                tokenRepo.save(mapping);
            }

            tokenMap.put(pii, newToken);
        }

        TokenResponse response = new TokenResponse();
        response.setTokenMap(tokenMap);
        return response;
    }

}
