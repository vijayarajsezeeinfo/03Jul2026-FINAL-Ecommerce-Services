package com.ezeeinfo.controller.io;

import com.ezeeinfo.dto.enumeration.UserRoleEM;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserIO extends BaseIO<UserIO> {

	private String username;
	private String password;
	private String email;
	private String mobile;
	private UserRoleEM role;
	private NamespaceIO namespace;

}
