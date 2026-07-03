package com.ezeeinfo.controller.io;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemIO extends BaseIO<OrderItemIO> {
	private OrderIO order;
	private ProductIO product;
	private Integer quantity;
	private Double price;
	private NamespaceIO namespace;
}
