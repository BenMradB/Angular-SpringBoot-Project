package com.bilel.SpringBoot_TP01.security;

public interface SecurityParams {
    public static final String SECRET_KEY = "645267556B58703273357638792F423F4528482B4D6250655368566D59713374";
    public static final long EXP_TIME = 10 * 24 * 60 * 60 * 1000;
    public static final String PREFIX = "Bearer ";
}
