package com.ezeeinfo.dto;

import com.ezeeinfo.dto.enumeration.UserRoleEM;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO<UserDTO> {

	private String username;
	private String password;
	private String email;
	private String mobile;
	private UserRoleEM role;
	private NamespaceDTO namespace;
}
