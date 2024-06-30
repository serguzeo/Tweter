package com.serguzeo.StartSpring.security;

public class SecurityConstants {
    public static final long JWT_EXPIRATION_TIME = Long.parseLong(System.getProperty("JWT_EXPIRATION_TIME"));
    public static final String JWT_SECRET = System.getProperty("JWT_SECRET");
}
