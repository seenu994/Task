package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum ProjectStatus {
	INDEVELOPMENT("INDEVELOPMENT"),
	INPRODUCTION("INPRODUCTION"),
	INACTIVE("INACTIVE");

	private String value;

	ProjectStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ProjectStatus toEnum(String value) {
		Optional<ProjectStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}
