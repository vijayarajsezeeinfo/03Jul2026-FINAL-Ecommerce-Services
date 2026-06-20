package com.ezeeinfo.dto.enumeration;

public enum PaymentModeEM {

	CASH("CASH", 0, "Cash"), CARD("CARD", 1, "Card"), NET_BANKING("NTBK", 2, "Net Banking"), UPI("UPI", 3, "Upi");

	private final String code;
	private final int id;
	private final String name;

	private PaymentModeEM(String code, int id, String name) {
		this.code = code;
		this.id = id;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static PaymentModeEM getPaymentModeEM(int id) {
		for (PaymentModeEM paymentMode : values()) {
			if (paymentMode.getId() == id) {
				return paymentMode;
			}
		}
		return null;
	}

	public static PaymentModeEM getPaymentModeEM(String code) {
		for (PaymentModeEM paymentMode : values()) {
			if (paymentMode.getCode().equalsIgnoreCase(code)) {
				return paymentMode;
			}
		}
		return null;
	}
}
