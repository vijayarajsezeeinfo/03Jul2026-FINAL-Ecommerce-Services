package com.ezeeinfo.dto.enumeration;

public enum OrderStatusEM {

	CANCELLED("CNLD", 0, "Cancelled"), HOLD("HOLD", 1, "Hold"), PLACED("PLCD", 2, "Placed"), IN_TRANSIT("TRST", 3, "In Transit"), OUT_FOR_DELIVERY("OTFD", 4, "Out For Delivery"), ATTEMPTED_DELIVERY("ATDD", 5, "Attempted Delivery"), DELIVERED("DVRD", 6, "Delivered");

	private final int id;
	private final String code;
	private final String name;

	private OrderStatusEM(String code, int id, String name) {
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

	public static OrderStatusEM getOrderStatusEM(int id) {
		for (OrderStatusEM orderStatus : values()) {
			if (orderStatus.getId() == id) {
				return orderStatus;
			}
		}
		return null;
	}

	public static OrderStatusEM getOrderStatusEM(String code) {
		for (OrderStatusEM orderStatus : values()) {
			if (orderStatus.getCode().equalsIgnoreCase(code)) {
				return orderStatus;
			}
		}
		return null;
	}
}
