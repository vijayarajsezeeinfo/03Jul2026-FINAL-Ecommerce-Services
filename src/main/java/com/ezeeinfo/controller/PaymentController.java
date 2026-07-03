package com.ezeeinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.controller.io.PaymentIO;
import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.service.PaymentService;
import com.ezeeinfo.util.TokenUtil;

@RestController
@RequestMapping("{authtoken}/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	@Autowired
	OrderRequestController orderRequestController;
	@Autowired
	TokenUtil tokenUtil;

	@RequestMapping(value = "/{namespaceCode}", method = RequestMethod.GET)
	public List<PaymentIO> getAllPayments(@PathVariable("authtoken") String authToken, @PathVariable("namespaceCode") String namespaceCode) {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return paymentService.getAllPayments(authDTO, namespaceCode).stream().map(dto -> orderRequestController.paymentDTOToIO(dto)).toList();
	}

	@RequestMapping(value = "/code/{paymentCode}", method = RequestMethod.GET)
	public PaymentIO getPaymentByCode(@PathVariable("authtoken") String authToken, @PathVariable("paymentCode") String paymentCode) throws Exception {
		AuthDTO authDTO = tokenUtil.getAuthDTO(authToken);
		return orderRequestController.paymentDTOToIO(paymentService.getPaymentByCode(authDTO, paymentCode));
	}
}
