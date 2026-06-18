package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.UserDTO;

public interface UserService {

	List<UserDTO> getAllUsers(String namespaceCode);

	UserDTO getUserByCode(String code);

	UserDTO update(UserDTO userDTO);
}
