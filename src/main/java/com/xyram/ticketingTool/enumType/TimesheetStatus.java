
package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum TimesheetStatus {
	
	PENDING("PENDING"),

	APPROVED("APPROVED"),
	
	REJECTED("REJECTED");

	private String value;

	TimesheetStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static TimesheetStatus toEnum(String value) {
		Optional<TimesheetStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}

}

