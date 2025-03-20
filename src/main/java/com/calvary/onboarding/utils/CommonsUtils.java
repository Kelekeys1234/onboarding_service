package com.calvary.onboarding.utils;

import org.apache.commons.codec.binary.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonsUtils {

    public static boolean isPasswordValid(String encryptPassword, String rawPassword, String salt) {
    	String hashedPassword = PasswordMapper.hashPassword(rawPassword, salt);
        return StringUtils.equals(encryptPassword, hashedPassword);
    }
}