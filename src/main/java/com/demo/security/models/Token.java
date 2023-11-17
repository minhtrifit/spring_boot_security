package com.demo.security.models;

public class Token {
    private String accessToken;
    private String refreshToken;

    public Token(String val1, String val2) {
        this.accessToken = val1;
        this.refreshToken = val2;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setAccesstoken(String value) {
        this.accessToken = value;
    }

    public void setRefreshToken(String value) {
        this.refreshToken = value;
    }
}
