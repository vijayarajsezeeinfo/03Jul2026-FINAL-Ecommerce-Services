package com.ezeeinfo.dto.enumeration;

public enum ShipmentStatusEM {

	PENDING("PNDG", 0, "Pending"), IN_TRANSIT("TRST", 1, "In Transit"), OUT_FOR_DELIVERY("OTFD", 2, "Out For Delivery"), DELIVERED("DVRD", 3, "Delivered");

	public final int id;
	public final String code;
	public final String name;

	ShipmentStatusEM(String code, int id, String name) {
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

	public static ShipmentStatusEM getShipmentStatusEM(int id) {
		for (ShipmentStatusEM shipmentStatus : values()) {
			if (shipmentStatus.getId() == id) {
				return shipmentStatus;
			}
		}
		return null;
	}

	public static ShipmentStatusEM getShipmentStatusEM(String code) {
		for (ShipmentStatusEM shipmentStatus : values()) {
			if (shipmentStatus.getCode().equalsIgnoreCase(code)) {
				return shipmentStatus;
			}
		}
		return null;
	}

}
