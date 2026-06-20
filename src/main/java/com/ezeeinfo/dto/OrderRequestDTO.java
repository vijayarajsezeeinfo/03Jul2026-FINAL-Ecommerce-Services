package com.ezeeinfo.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {

	private OrderDTO order;
	private PaymentDTO payment;
	private List<OrderItemDTO> orderItems;
}
