package com.cipherone.tokenizer.model;

import java.util.Map;

/**
 * @author sachchidanand singh
 */

public class TokenResponse {
    private Map<String, String> tokenMap;

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public void setTokenMap(Map<String, String> tokenMap) {
        this.tokenMap = tokenMap;
    }
}
