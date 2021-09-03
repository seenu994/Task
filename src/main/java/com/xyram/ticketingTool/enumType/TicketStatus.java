package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum TicketStatus {
	INITIATED("INDEVELOPMENT"),

	ONREVIEW("INPRODUCTION"),
	ONHOLD("ONHOLD"),
	COMPLETED("COMPLETED"),
	REOPEN("REOPEN"),
	CANCELLED("CANCELLED"),
	NOTCOMPLETED("NOTCOMPLETED");

	private String value;

	TicketStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static TicketStatus toEnum(String value) {
		Optional<TicketStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}