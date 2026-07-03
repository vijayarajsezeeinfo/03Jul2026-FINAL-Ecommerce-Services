package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.PaymentDTO;

public interface PaymentService {

	List<PaymentDTO> getAllPayments(AuthDTO authDTO, String namespaceCode);

	PaymentDTO getPaymentByCode(AuthDTO authDTO, String code);

}