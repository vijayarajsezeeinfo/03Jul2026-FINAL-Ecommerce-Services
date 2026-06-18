package com.ezeeinfo.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			String auth = request.getHeader("Authorization");
			log.info("Authorization Header : {}", auth);
			if (auth != null && auth.startsWith("Bearer ")) {
				String token = auth.substring(7);
				token = token.replace("\"", "");
				Integer userId = JwtUtil.getUserId(token);
				log.info("User Id From JWT : {}", userId);
				SecurityUtil.setUserId(userId);
			}
			filterChain.doFilter(request, response);
		}
		finally {
			SecurityUtil.clear();
		}
	}

}
