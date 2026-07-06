package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.ProductDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.ProductDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public List<ProductDTO> getAllProducts(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("Namespace {} not found to fetch all products", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		if (!namespaceCode.equals(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
		}
		return productDAO.getAllProducts(namespaceCode);
	}

	@Override
	public ProductDTO getProductByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		ProductDTO productDTO = productDAO.getProductByCode(code);

		if (!productDTO.getNamespace().getCode().equals(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
		}
		return productDTO;
	}

	@Override
	public List<ProductDTO> getProductsByNamePriceAndNamespace(AuthDTO authDTO, String name, Double price, String namespaceCode) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		if (!namespaceCode.equals(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW PRODUCTS");
		}

		LOG.info("Getting products by NAME, PRICE and NAMESPACE");
		return productDAO.getProductsByNamePriceAndNamespace(name, price, namespaceCode);
	}

	@Override
	public ProductDTO update(AuthDTO authDTO, ProductDTO productDTO) {

		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		productDTO.setUpdatedBy(loggedInUser);
		LOG.info("product dto: {}", productDTO);

		if (!productDTO.getNamespace().getCode().equals(loggedInUser.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY PRODUCT");
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY PRODUCT");
		}

		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY ADMIN CAN SAVE/MODIFY PRODUCT");
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN SAVE/MODIFY PRODUCT");
		}
		return productDAO.update(productDTO);
	}

}
