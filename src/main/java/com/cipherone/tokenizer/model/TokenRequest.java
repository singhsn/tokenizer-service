package com.cipherone.tokenizer.model;

import java.util.Map;

/**
 * @author sachchidanand singh
 */

public class TokenRequest {
    private Map<String, String> piiMap;

    public Map<String, String> getPiiMap() {
        return piiMap;
    }

    public void setPiiMap(Map<String, String> piiMap) {
        this.piiMap = piiMap;
    }
}
