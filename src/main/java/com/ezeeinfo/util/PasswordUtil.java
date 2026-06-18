package com.ezeeinfo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	public static String hashPassword(String password) {
		return ENCODER.encode(password);
	}

	public static boolean verifyPassword(String rawPassword, String hashedPassword) {
		return ENCODER.matches(rawPassword, hashedPassword);
	}
}
