package com.xyram.ticketingTool.enumType;


import java.util.Arrays;
import java.util.Optional;

public enum ClientStatus {
	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE");
	
	
	private String value;

	ClientStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ClientStatus toEnum(String value) {
		Optional<ClientStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}
