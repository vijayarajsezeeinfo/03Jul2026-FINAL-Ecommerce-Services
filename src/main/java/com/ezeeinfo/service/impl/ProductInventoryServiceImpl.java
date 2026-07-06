package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.ProductInventoryDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.ProductInventoryDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.ProductInventoryService;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
	@Autowired
	ProductInventoryDAO productInventoryDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

	@Override
	public List<ProductInventoryDTO> getAllProductInventories(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("Namespace {} not found to fetch all product inventories", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		// ONLY SAMENAMESPACE USER CAN VIEW
		if (!namespaceCode.equalsIgnoreCase(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE ADMIN CAN VIEW PRODUCT INVENTORY");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE ADMIN CAN VIEW PRODUCT INVENTORY");
		}

		// ONLY ADMIN CAN VIEW
		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW PRODUCT INVENTORY");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW PRODUCT INVENTORY");
		}

		return productInventoryDAO.getAllProductInventories(namespaceCode);
	}

	@Override
	public ProductInventoryDTO getProductInventoryByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		ProductInventoryDTO productInventoryDTO = productInventoryDAO.getProductInventoryByCode(code);

		// ONLY SAMENAMESPACE ADMIN CAN VIEW
		if (!productInventoryDTO.getNamespace().getCode().equalsIgnoreCase(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE ADMIN CAN VIEW PRODUCT INVENTORY");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE ADMIN CAN VIEW PRODUCT INVENTORY");
		}

		// ONLY ADMIN CAN VIEW
		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW PRODUCT INVENTORY");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW PRODUCT INVENTORY");
		}
		return productInventoryDTO;
	}

	@Override
	public ProductInventoryDTO update(AuthDTO authDTO, ProductInventoryDTO productInventoryDTO) {

		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		productInventoryDTO.setUpdatedBy(loggedInUser);

		if (!productInventoryDTO.getNamespace().getCode().equalsIgnoreCase(loggedInUser.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY PRODUCT INVENTORY");
		}

		if (loggedInUser.getRole().getId() != 1) {
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN SAVE/MODIFY PRODUCT INVENTORY");
		}
		return productInventoryDAO.update(productInventoryDTO);
	}

}
