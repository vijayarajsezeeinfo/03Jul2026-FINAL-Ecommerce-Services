package com.ezeeinfo.service.impl;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.LoginRequestDTO;
import com.ezeeinfo.dto.LoginResponseDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.service.AuthService;
import com.ezeeinfo.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CacheManager cacheManager;

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		UserDTO userDTO = userDAO.login(request.getUserCode(), request.getUsername(), request.getPassword(), request.getNamespaceCode());

		String token = JwtUtil.generateToken(userDTO);

		Cache<String, AuthDTO> tokenCache = cacheManager.getCache("tokenCache", String.class, AuthDTO.class);
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUser(userDTO);

		// SETTING TOKEN IN CACHE
		tokenCache.put(token, authDTO);

		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setToken(token);

		return loginResponseDTO;
	}

}
