package com.ezeeinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.AddressDAO;
import com.ezeeinfo.dto.AddressDTO;
import com.ezeeinfo.service.AddressService;
import com.ezeeinfo.util.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressDAO addressDAO;

	@Override
	public List<AddressDTO> getAllAddresses(String namespaceCode) {
		// TODO Auto-generated method stub
		log.info("{}",addressDAO.getAllAddresses(namespaceCode));
		return addressDAO.getAllAddresses(namespaceCode);
	}

	@Override
	public AddressDTO getAddressByCode(String code) {
		// TODO Auto-generated method stub
		log.info("{}",addressDAO.getAddressByCode(code));
		return addressDAO.getAddressByCode(code);
	}

	@Override
	public AddressDTO update(AddressDTO addressDTO) {
		// TODO Auto-generated method stub
		log.info("entered AddressServiceImpl.update");
		log.info("Updated by : {}",addressDTO);
//		log.info("{}",addressDAO.update(addressDTO));
		addressDTO.setUpdatedBy(SecurityUtil.getUserId());
		return addressDAO.update(addressDTO);
	}

}
