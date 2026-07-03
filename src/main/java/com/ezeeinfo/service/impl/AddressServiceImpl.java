package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.AddressDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AddressDTO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.AddressService;

@Service

public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressDAO addressDAO;
	@Autowired
	UserDAO userDAO;

	private static final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Override
	public List<AddressDTO> getAllAddresses(AuthDTO authDTO, String namespaceCode) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW");
		}

		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY ADMIN CAN VIEW");
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN VIEW");
		}
		return addressDAO.getAllAddresses(namespaceCode);
	}

	@Override
	public AddressDTO getAddressByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		AddressDTO addressDTO = addressDAO.getAddressByCode(code);
		UserDTO addressOwningUser = userDAO.getUserByCodeInternal(addressDTO.getUser().getCode());

		// ONLY SAMENAMESPACE USER CAN VIEW
		if (!addressDTO.getNamespace().getCode().equalsIgnoreCase(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE ADDRESS {}", code);
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW THE ADDRESS " + code);
		}

		// ONLY ADMIN CAN VIEW ANY ADDRESS.
		// NORMAL USER CAN VIEW ONLY THEIR OWN ADDRESS.
		if (!loggedInUser.getCode().equalsIgnoreCase(addressOwningUser.getCode())) {
			if (loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY ADMIN / RESPECTIVE USER CAN VIEW");
				throw new ServiceException("EXCEPTION 403: ONLY ADMIN / RESPECTIVE USER CAN VIEW");
			}
		}

		LOG.info("{}", addressDAO.getAddressByCode(code));
		return addressDTO;
	}

	@Override
	public AddressDTO update(AuthDTO authDTO, AddressDTO addressDTO) {
		LOG.info("Input Address : {}", addressDTO);

		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		addressDTO.setUpdatedBy(loggedInUser);
		UserDTO addressOwningUser = userDAO.getUserByCodeInternal(addressDTO.getUser().getCode());

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(addressDTO.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY THE USER ADDRESS");
		}

		if (!addressDTO.getCode().equalsIgnoreCase("null")) {
			if (!loggedInUser.getCode().equalsIgnoreCase(addressOwningUser.getCode()) || loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY RESPECTIVE USER OR ADMIN CAN SAVE/MODIFY THE USER ADDRESS");
				throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE USER OR ADMIN CAN SAVE/MODIFY THE USER ADDRESS");
			}
		}

		// When adding new address only admin or respective user can add their
		// address
		if (addressDTO.getCode().equalsIgnoreCase("null")) {
			if (!addressDTO.getUser().getCode().equalsIgnoreCase(loggedInUser.getCode()) && loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY RESPECTIVE USER OR ADMIN CAN SAVE/MODIFY THE USER ADDRESS");
				throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE USER OR ADMIN CAN SAVE/MODIFY THE USER ADDRESS");
			}
		}

		return addressDAO.update(addressDTO);
	}

}
