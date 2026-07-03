package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AddressDTO;
import com.ezeeinfo.dto.AuthDTO;

public interface AddressService {

	List<AddressDTO> getAllAddresses(AuthDTO authDTO, String namespaceCode);

	AddressDTO getAddressByCode(AuthDTO authDTO, String code);

	AddressDTO update(AuthDTO authDTO, AddressDTO addressDTO);
}
