package com.ezeeinfo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemDTO extends BaseDTO {

	private OrderDTO order;
	private ProductDTO product;
	private Integer quantity;
	private Double price;
	private NamespaceDTO namespace;

}
