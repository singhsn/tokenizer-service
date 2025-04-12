package com.cipherone.tokenizer.repository;

import com.cipherone.tokenizer.entity.TokenMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author sachchidanand singh
 */

public interface TokenMappingRepository extends JpaRepository<TokenMapping, Long> {
    Optional<TokenMapping> findByPiiValue(String piiValue);
    Optional<TokenMapping> findByToken(String token);
}
