package com.ezeeinfo.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressIO extends BaseIO<AddressIO>{

	private String doorNo;
	private String street;
	private String place;
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	private UserIOResponse user;
	private NamespaceIO namespace;
	
}
