package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.BrandDAO;
import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.BrandDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandDAO brandDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Override
	public List<BrandDTO> getAllBrands(AuthDTO authDTO, String namespaceCode) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		// Checking if given namespace exists or not
		if (namespaceDAO.getNamespaceByCode(namespaceCode) == null) {
			LOG.info("Namespace {} not found to fetch all brands", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			LOG.info("EXCEPTION 403: ONLY USER FROM {} CAN VIEW ALL", namespaceCode);
			throw new ServiceException("EXCEPTION 403: ONLY USER FROM " + namespaceCode + " CAN VIEW ALL");
		}

		return brandDAO.getAllBrands(namespaceCode);
	}

	@Override
	public BrandDTO getBrandByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		BrandDTO brandDTO = brandDAO.getBrandByCode(code);

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(brandDTO.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403:  ONLY USER FROM {} CAN VIEW ALL", brandDTO.getNamespace().getCode());
			throw new ServiceException("EXCEPTION 403: ONLY USER FROM " + brandDTO.getNamespace().getCode() + " CAN VIEW");
		}

		return brandDTO;
	}

	@Override
	public BrandDTO update(AuthDTO authDTO, BrandDTO brandDTO) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		brandDTO.setUpdatedBy(loggedInUser);

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(brandDTO.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY BRAND");
		}

		if (loggedInUser.getRole().getId() != 1) {
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN SAVE/MODIFY BRAND");
		}
		return brandDAO.update(brandDTO);
	}

}
