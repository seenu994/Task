package com.xyram.ticketingTool.entity;import java.util.Arrays;
import java.util.Optional;

public enum AssetVendorStatus {
	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE");
	
	
	private String value;

	AssetVendorStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static AssetVendorStatus toEnum(String value) {
		Optional<AssetVendorStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}
