package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.UserDTO;

public interface UserService {

	List<UserDTO> getAllUsers(AuthDTO authDTO, String namespaceCode);

	UserDTO getUserByCode(AuthDTO authDTO, String code) throws Exception;

	UserDTO update(AuthDTO authDTO, UserDTO userDTO);
}
