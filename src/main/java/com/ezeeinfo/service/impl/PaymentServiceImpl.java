package com.ezeeinfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.PaymentDAO;
import com.ezeeinfo.dao.UserDAO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.PaymentDTO;
import com.ezeeinfo.dto.UserDTO;
import com.ezeeinfo.exception.ServiceException;
import com.ezeeinfo.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;
	@Autowired
	UserDAO userDAO;

	private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Override
	public List<PaymentDTO> getAllPayments(AuthDTO authDTO, String namespaceCode) {
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
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW ALL");
		}

		// ONLY ADMIN CAN VIEW PAYMENTS
		if (loggedInUser.getRole().getId() != 1) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE ADMIN CAN VIEW ALL");
		}
		return paymentDAO.getAllPayments(namespaceCode);
	}

	@Override
	public PaymentDTO getPaymentByCode(AuthDTO authDTO, String code) {
		if (authDTO == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}
		if (authDTO.getUser().getId() == null) {
			LOG.info("Login not done. So AuthDTO is null");
			throw new ServiceException("Please Login First");
		}

		UserDTO loggedInUser = userDAO.getUser(authDTO.getUser().getId());
		PaymentDTO paymentDTO = paymentDAO.getPaymentByCode(code);

		if (!loggedInUser.getNamespace().getCode().equalsIgnoreCase(paymentDTO.getNamespace().getCode())) {
			LOG.info("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW");
			throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE NAMESPACE ADMIN CAN VIEW");
		}

		if (!loggedInUser.getCode().equalsIgnoreCase(paymentDTO.getOrder().getUser().getCode())) {
			if (loggedInUser.getRole().getId() != 1) {
				LOG.info("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW");
				throw new ServiceException("EXCEPTION 403: ONLY RESPECTIVE USER / ADMIN CAN VIEW");
			}
		}

		return paymentDTO;
	}

}
