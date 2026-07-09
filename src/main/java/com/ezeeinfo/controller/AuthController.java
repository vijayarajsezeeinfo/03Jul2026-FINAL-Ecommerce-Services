package com.ezeeinfo.controller;

import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.LoginRequestIO;
import com.ezeeinfo.controller.io.LoginResponseIO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.LoginRequestDTO;
import com.ezeeinfo.dto.LoginResponseDTO;
import com.ezeeinfo.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CacheManager cacheManager;
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponseIO login(@RequestBody LoginRequestIO request) {

		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setUserCode(request.getUserCode());
		loginRequestDTO.setUsername(request.getUsername());
		loginRequestDTO.setPassword(request.getPassword());
		loginRequestDTO.setNamespaceCode(request.getNamespaceCode());

		LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
		LoginResponseIO loginResponseIO = new LoginResponseIO();
		loginResponseIO.setToken(loginResponseDTO.getToken());
		return loginResponseIO;
	}

	@RequestMapping(value = "/{authtoken}/logout", method = RequestMethod.GET)
	public String logout(@PathVariable("authtoken") String authToken) {
		return authService.logout(authToken);
	}

}
