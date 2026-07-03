package com.ezeeinfo.dto;

import java.time.LocalDateTime;

import com.ezeeinfo.dto.enumeration.OrderStatusEM;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends BaseDTO<OrderDTO> {
	private UserDTO user;
	private OrderStatusEM orderStatus;
	private Double totalAmount;
	private LocalDateTime orderDate;
	private NamespaceDTO namespace;
}
