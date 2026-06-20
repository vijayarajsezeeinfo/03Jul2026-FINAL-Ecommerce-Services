package com.ezeeinfo.dto.enumeration;

public enum BillingStatusEM {

	NOT_PAID("NTPD", 0, "Not Paid"), PENDING("PNDG", 1, "Pending"), PARTIALLY_PAID("PLPD", 2, "Partially Paid"), PAID("PAID", 3, "Paid");

	public final int id;
	public final String code;
	public final String name;

	private BillingStatusEM(String code, int id, String name) {
		this.code = code;
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static BillingStatusEM getBillingStatusEM(int id) {
		for (BillingStatusEM billingStatus : values()) {
			if (billingStatus.getId() == id) {
				return billingStatus;
			}
		}
		return null;
	}

	public static BillingStatusEM getBillingStatusEM(String code) {
		for (BillingStatusEM billingStatus : values()) {
			if (billingStatus.getCode().equalsIgnoreCase(code)) {
				return billingStatus;
			}
		}
		return null;
	}

}
