package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.CartItemDAO;
import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.CartItemDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	CartItemDAO cartItemDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(CartItemServiceImpl.class);

	@Override
	public List<CartItemDTO> getAllCartItems(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("Namespace {} not found to fetch all cart items", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL");
		}

		// ONLY RESPECTIVE ADMIN CAN VIEW ALL
		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL");
		}
		return cartItemDAO.getAllCartItems(namespaceCode);
	}

	@Override
	public CartItemDTO getCartItemByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		CartItemDTO cartItemDTO = cartItemDAO.getCartItemByCode(code);
		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(cartItemDTO.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE USER / ADMIN CAN VIEW");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE USER / ADMIN CAN VIEW");
		}

		// ONLY RESPECTIVE USER / ADMIN CAN VIEW
		if (!loggedInUser.getCode().equalsIgnoreCase(cartItemDTO.getCart().getUser().getCode())) {
			if (loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW");
				throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW");
			}
		}
		return cartItemDTO;
	}

	@Override
	public CartItemDTO update(AuthDTO authDTO, CartItemDTO cartItemDTO) {

		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		cartItemDTO.setUpdatedBy(loggedInUser);

		UserDTO cartOwningUser = userDAO.getUserByCodeInternal(cartItemDTO.getCart().getUser().getCode());

		// ONLY RESPECTIVE USER CAN ADD CART ITEM
		if (loggedInUser.getId() != cartOwningUser.getId()) {
			LOG.info("EXCEPTION 403: ONLY CART OWNER CAN ADD CART ITEM");
			throw new ServiceException("EXCEPTION 403: ONLY CART OWNER CAN ADD CART ITEM");
		}

		return cartItemDAO.update(cartItemDTO);
	}
}