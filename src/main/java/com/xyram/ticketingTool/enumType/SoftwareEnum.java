package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum SoftwareEnum {
	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE");

	private String value;

	SoftwareEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static SoftwareEnum toEnum(String value) {
		Optional<SoftwareEnum> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}



