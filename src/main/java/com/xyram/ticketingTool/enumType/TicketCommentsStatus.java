package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum TicketCommentsStatus {
	ACTIVE("ACTIVE"),

	EDITED("EDITED");

	private String value;

	TicketCommentsStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static TicketCommentsStatus toEnum(String value) {
		Optional<TicketCommentsStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}