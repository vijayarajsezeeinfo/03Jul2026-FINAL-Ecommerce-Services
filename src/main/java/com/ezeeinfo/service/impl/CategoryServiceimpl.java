package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.CategoryDAO;
import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CategoryDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceimpl.class);

	@Override
	public List<CategoryDTO> getAllCategories(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("Namespace {} not found to fetch all categories", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW CATEGORY");
		}
		return categoryDAO.getAllCategories(namespaceCode);
	}

	@Override
	public CategoryDTO getCategoryByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		CategoryDTO categoryDTO = categoryDAO.getCategoryByCode(code);

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(categoryDTO.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN VIEW CATEGORY");
		}
		return categoryDTO;
	}

	@Override
	public CategoryDTO update(AuthDTO authDTO, CategoryDTO categoryDTO) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		categoryDTO.setUpdatedBy(loggedInUser);

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(categoryDTO.getNamespace().getCode())) {
			throw new ServiceException("EXCEPTION 403: ONLY SAME NAMESPACE USER CAN SAVE/MODIFY CATEGORY");
		}

		if (loggedInUser.getRole().getId() != 1) {
			throw new ServiceException("EXCEPTION 403: ONLY ADMIN CAN SAVE/MODIFY CATEGORY");
		}

		return categoryDAO.update(categoryDTO);
	}

}
