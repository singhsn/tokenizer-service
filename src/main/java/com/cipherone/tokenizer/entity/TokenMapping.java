package com.cipherone.tokenizer.entity;

import jakarta.persistence.*;

/**
 * @author sachchidanand singh
 */

@Entity
@Table(name = "token_mapping")
public class TokenMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Column
    private String piiValue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getPiiValue() { return piiValue; }
    public void setPiiValue(String piiValue) { this.piiValue = piiValue; }
}
