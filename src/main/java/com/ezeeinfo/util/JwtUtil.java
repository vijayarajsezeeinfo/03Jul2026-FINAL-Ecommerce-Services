package com.ezeeinfo.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	private static final String SECRET = "ezeeinfo-secret-key-ezeeinfo-secret-key";
	private static final Key KEY = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

	public static String generateToken(Integer userId, String userCode, String username, String role) {
		return Jwts.builder().claim("userId", userId).claim("userCode", userCode).claim("userName", username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 86400000)).signWith(KEY).compact();
	}

	public static Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
	}

	public static Integer getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("userId", Integer.class);
	}

	public static String getUserCode(String token) {
		Claims claims = getClaims(token);
		return claims.get("userCode", String.class);
	}
}
