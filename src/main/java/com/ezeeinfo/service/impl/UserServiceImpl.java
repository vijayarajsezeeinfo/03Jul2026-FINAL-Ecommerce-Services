package com.ezeeinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.service.UserService;
import com.ezeeinfo.util.PasswordUtil;
import com.ezeeinfo.util.SecurityUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Override
	public List<UserDTO> getAllUsers(String namespaceCode) {
		// TODO Auto-generated method stub
		return userDAO.getAllUsers(namespaceCode);
	}

	@Override
	public UserDTO getUserByCode(String code) {
		// TODO Auto-generated method stub
		return userDAO.getUserByCode(code);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		// TODO Auto-generated method stub
		userDTO.setPassword(PasswordUtil.hashPassword(userDTO.getPassword()));
		userDTO.setUpdatedBy(SecurityUtil.getUserId());
		return userDAO.update(userDTO);
	}

}
