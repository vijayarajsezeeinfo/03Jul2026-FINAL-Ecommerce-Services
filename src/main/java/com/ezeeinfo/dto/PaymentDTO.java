package com.ezeeinfo.dto;

import com.ezeeinfo.dto.enumeration.BillingStatusEM;
import com.ezeeinfo.dto.enumeration.PaymentModeEM;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentDTO extends BaseDTO {
	private OrderDTO order;
	private PaymentModeEM paymentMode;
	private Double totalAmountToPay;
	private Double paidAmount;
	private Double balanceAmount;
	private BillingStatusEM billingStatus;
	private String transactionId;
	private String remarks;
	private NamespaceDTO namespace;
}
