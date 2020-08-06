package com.vuidoan.product.security;

public class SecurityConstant {
    static final String SECRET_KEY = "$#@MySecretKey123321*&^";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String AUTH_HEADER = "Authorization";
    static final long EXPIRATION_TIME = 3600000; // 1 hour
}
