package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum AssetIssueStatus 
{
	OPEN("OPEN"),

	CLOSE("CLOSE"),
	
	DAMAGE("DAMAGE");

	private String value;

	AssetIssueStatus (String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static AssetIssueStatus  toEnum(String value) {
		Optional<AssetIssueStatus > optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}