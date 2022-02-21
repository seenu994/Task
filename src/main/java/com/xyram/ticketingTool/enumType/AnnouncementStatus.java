package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum AnnouncementStatus {
	
	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE"),
	
	OFFLINE("OFFLINE");

	private String value;

	AnnouncementStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static AnnouncementStatus toEnum(String value) {
		Optional<AnnouncementStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}

}
