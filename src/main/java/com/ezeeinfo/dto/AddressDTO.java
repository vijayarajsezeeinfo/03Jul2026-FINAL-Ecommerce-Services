package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressDTO extends BaseDTO{

	private String doorNo;
	private String street;
	private String place;
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	private UserDTO user;
	private NamespaceDTO namespace;
}
