package com.cipherone.tokenizer.controller;

import com.cipherone.tokenizer.model.TokenRequest;
import com.cipherone.tokenizer.model.TokenResponse;
import com.cipherone.tokenizer.service.TokenizerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sachchidanand singh
 */

@RestController
@RequestMapping("/api/tokenizer")
public class TokenController {

    @Autowired
    private TokenizerService tokenizerService;

    @Operation(summary = "Generate tokens for PII values")
    @PostMapping("/generate")
    public TokenResponse generateTokens(@RequestBody TokenRequest request) {
        return tokenizerService.generateTokens(request);
    }

    @Operation(summary = "Resolve a token to its original PII value")
    @GetMapping("/resolve/{token}")
    public String resolveToken(@PathVariable String token) {
        return tokenizerService.resolveToken(token);
    }

    @Operation(summary = "Refresh a token for the given PII")
    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestBody TokenRequest request) {
        return tokenizerService.refreshTokens(request);
    }

}
