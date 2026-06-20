package com.ezeeinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezeeinfo.dao.OrderDAO;
import com.ezeeinfo.dto.OrderItemDTO;
import com.ezeeinfo.dto.OrderRequestDTO;
import com.ezeeinfo.service.OrderRequestService;
import com.ezeeinfo.util.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderRequestServiceImpl implements OrderRequestService {

	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public OrderRequestDTO update(OrderRequestDTO orderRequestDTO) {
		// TODO Auto-generated method stub
		log.info("OrderRequest DTO : {}",orderRequestDTO);

		orderRequestDTO.getOrder().setUpdatedBy(SecurityUtil.getUserId());
        for(OrderItemDTO item:orderRequestDTO.getOrderItems()) {
        	item.setUpdatedBy(SecurityUtil.getUserId());
        }
		orderRequestDTO.getPayment().setUpdatedBy(SecurityUtil.getUserId());
		return orderDAO.update(orderRequestDTO);
	}

}
