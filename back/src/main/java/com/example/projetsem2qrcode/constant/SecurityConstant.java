package com.example.projetsem2qrcode.constant;

public class SecurityConstant {

    public static final String TOKEN_PREFIX = "Bearer "; //ownership , no need more checks
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    public static final String AUTHORITIES = "Authorities";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String [] PUBLIC_URLS= {"/fiche/liste/**", "/user/login" , "/user/register/**","/user/image/**"};

}
