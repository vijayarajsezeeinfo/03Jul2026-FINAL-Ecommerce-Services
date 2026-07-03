package com.ezeeinfo.service;

import java.util.List;

import com.ezeeinfo.dto.AuthDTO;
import com.ezeeinfo.dto.OrderRequestDTO;

public interface OrderRequestService {

	List<OrderRequestDTO> getAllOrders(AuthDTO authDTO, String namespaceCode);

	OrderRequestDTO getOrderByCode(AuthDTO authDTO, String code);

	OrderRequestDTO update(AuthDTO authDTO, OrderRequestDTO orderRequestDTO);
}
