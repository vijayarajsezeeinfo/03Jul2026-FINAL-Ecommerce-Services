package com.ezeeinfo.service.impl;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.LoginRequestDTO;
import com.ezeeinfo.dto.LoginResponseDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.AuthService;
import com.ezeeinfo.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CacheManager cacheManager;

	private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		UserDTO userDTO = userDAO.login(request.getUserCode(), request.getUsername(), request.getPassword(), request.getNamespaceCode());

		String token = JwtUtil.generateToken(userDTO);

		Cache<String, AuthDTO> tokenCache = cacheManager.getCache("tokenCache", String.class, AuthDTO.class);
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUser(userDTO);

		// SETTING TOKEN IN CACHE
		tokenCache.put(token, authDTO);

		LOG.info("Login successful for User: {}", userDTO.getUsername());
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setToken(token);

		return loginResponseDTO;
	}

	@Override
	public String logout(String authToken) {

		Cache<String, AuthDTO> tokenCache = cacheManager.getCache("tokenCache", String.class, AuthDTO.class);
		AuthDTO authDTO = tokenCache.get(authToken);
		if (authDTO == null) {
			LOG.info("INVALID TOKEN: {}", authToken);
			throw new ServiceException("INVALID TOKEN");
		}

		// REMOVING TOKEN FROM TOKEN CACHE
		tokenCache.remove(authToken);

		LOG.info("Logout successful for User : {} ", authDTO.getUser().getUsername());
		String response = "Logout successful for User : " + authDTO.getUser().getUsername();
		return response;
	}

}
