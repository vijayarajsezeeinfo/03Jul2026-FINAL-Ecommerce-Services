package com.ezeeinfo.service;

import com.ezeeinfo.dto.LoginRequestDTO;
import com.ezeeinfo.dto.LoginResponseDTO;

public interface AuthService {
	LoginResponseDTO login(LoginRequestDTO request);
}
