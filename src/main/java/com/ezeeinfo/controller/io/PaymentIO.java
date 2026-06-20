package com.ezeeinfo.controller.io;

import com.ezeeinfo.dto.enumeration.BillingStatusEM;
import com.ezeeinfo.dto.enumeration.PaymentModeEM;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentIO extends BaseIO {
	private OrderIO order;
	private PaymentModeEM paymentMode;
	private Double totalAmountToPay;
	private Double paidAmount;
	private Double balanceAmount;
	private BillingStatusEM billingStatus;
	private String transactionId;
	private String remarks;
	private NamespaceIO namespace;
}
