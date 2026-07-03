package com.ezeeinfo.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

	private String userCode;
	private String username;
	private String password;
	private String namespaceCode;
}
