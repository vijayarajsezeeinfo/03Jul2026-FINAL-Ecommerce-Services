package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.NamespaceDAO;
import com.ezeeinfo.dao.OrderDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.OrderItemDTO;
import com.ezeeinfo.dto.OrderRequestDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.OrderRequestService;

@Service

public class OrderRequestServiceImpl implements OrderRequestService {

	@Autowired
	OrderDAO orderDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	NamespaceDAO namespaceDAO;

	private static final Logger LOG = LoggerFactory.getLogger(OrderRequestServiceImpl.class);

	@Override
	public OrderRequestDTO getOrderByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		OrderRequestDTO orderRequestDTO = orderDAO.getOrderByCode(code);

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(orderRequestDTO.getOrder().getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ORDER");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ORDER");
		}

		if (!loggedInUser.getCode().equals(orderRequestDTO.getOrder().getUser().getCode())) {
			if (loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW ORDER");
				throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW ORDER");
			}
		}
		return orderRequestDTO;
	}

	@Override
	public OrderRequestDTO update(AuthDTO authDTO, OrderRequestDTO orderRequestDTO) {
		LOG.info("OrderRequest DTO : {}", orderRequestDTO);
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());

		// SETTING UPDATED BY FOR ORDERS
		orderRequestDTO.getOrder().setUpdatedBy(loggedInUser);

		// SETTING UPDATED BY FOR ORDER ITEMS
		for (OrderItemDTO item : orderRequestDTO.getOrderItems()) {
			item.setUpdatedBy(loggedInUser);
			if (!item.getNamespace().getCode().equalsIgnoreCase(orderRequestDTO.getOrder().getNamespace().getCode())) {
				LOG.info("EXCEPTION 403: ORDER NAMESPACE AND ORDER ITEM NAMESPACE NOT MATCH. Item Namespace : {}, Order Namespace : {}", item.getNamespace().getCode(), orderRequestDTO.getOrder().getNamespace().getCode());
				throw new ServiceException("EXCEPTION 403: ORDER NAMESPACE AND ORDER ITEM NAMESPACE NOT MATCH");
			}
		}

		// SETTING UPDATED BY FOR PAYMENTS
		orderRequestDTO.getPayment().setUpdatedBy(loggedInUser);

		if (!orderRequestDTO.getOrder().getNamespace().getCode().equalsIgnoreCase(orderRequestDTO.getOrder().getUser().getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ORDER NAMESPACE AND USER NAMESPACE NOT MATCH. Order Namespace : {}, User Namespace : {}", orderRequestDTO.getOrder().getNamespace().getCode(), orderRequestDTO.getOrder().getUser().getNamespace().getCode());
			throw new ServiceException("EXCEPTION 403: ORDER NAMESPACE AND USER NAMESPACE NOT MATCH");
		}

		if (!loggedInUser.getCode().equalsIgnoreCase(orderRequestDTO.getOrder().getUser().getCode())) {
			LOG.info("EXCEPTION 403: ONLY VALID USER CAN ORDER. Logged In User : {}. Order's User : {}", loggedInUser.getCode(), orderRequestDTO.getOrder().getUser().getCode());
			throw new ServiceException("EXCEPTION 403: LOGGED IN USER AND ORDER USER NOT MATCH");
		}

		return orderDAO.update(orderRequestDTO);
	}

	@Override
	public List<OrderRequestDTO> getAllOrders(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("Namespace {} not found to fetch all orders", namespaceCode);
			throw new ServiceException("EXCEPTION 404: Namespace Not Found");
		}

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(namespaceCode)) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL ORDERS");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL ORDERS");
		}

		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL ORDERS");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL ORDERS");
		}
		return orderDAO.getAllOrders(namespaceCode);
	}

}
