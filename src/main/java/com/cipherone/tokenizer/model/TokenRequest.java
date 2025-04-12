package com.cipherone.tokenizer.model;

import java.util.List;

/**
 * @author sachchidanand singh
 */

public class TokenRequest {
    private List<String> piiValues;

    public List<String> getPiiValues() {
        return piiValues;
    }

    public void setPiiValues(List<String> piiValues) {
        this.piiValues = piiValues;
    }
}
