package com.ezeeinfo.controller.io;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestIO {

	private OrderIO order;
	private PaymentIO payment;
	private List<OrderItemIO> orderItems;

}
