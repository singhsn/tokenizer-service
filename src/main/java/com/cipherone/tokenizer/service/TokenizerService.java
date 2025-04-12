package com.cipherone.tokenizer.service;

import com.cipherone.tokenizer.entity.TokenMapping;
import com.cipherone.tokenizer.model.TokenRequest;
import com.cipherone.tokenizer.model.TokenResponse;
import com.cipherone.tokenizer.repository.TokenMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenizerService {

    @Autowired
    private TokenMappingRepository tokenRepo;

    public TokenResponse generateTokens(TokenRequest request) {
        Map<String, String> tokenMap = new HashMap<>();

        for (String pii : request.getPiiValues()) {
            String token = tokenRepo.findByPiiValue(pii)
                    .map(TokenMapping::getToken)
                    .orElseGet(() -> {
                        String newToken = UUID.randomUUID().toString();
                        TokenMapping mapping = new TokenMapping();
                        mapping.setPiiValue(pii);
                        mapping.setToken(newToken);
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
}
