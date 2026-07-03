package com.ezeeinfo.controller.io;

import lombok.Data;

@Data
public class BaseIO<T> {

	private String code;
	private String name;
	private Integer activeFlag;
}
