
package com.xyram.ticketingTool.enumType;
import java.util.Arrays;
import java.util.Optional;


public enum ArticleStatus {
	ACTIVE("ACTIVE"),

	INACTIVE("INACTIVE"),
	
	OFFLINE("OFFLINE");

	private String value;

	ArticleStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ArticleStatus toEnum(String value) {
		Optional<ArticleStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}