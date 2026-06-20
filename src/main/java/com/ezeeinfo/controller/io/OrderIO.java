package com.ezeeinfo.controller.io;

import java.time.LocalDateTime;

import com.ezeeinfo.dto.enumeration.OrderStatusEM;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderIO extends BaseIO {

	private UserIOResponse user;
	private OrderStatusEM orderStatus;
	private Double totalAmount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderDate;
	private NamespaceIO namespace;

}
