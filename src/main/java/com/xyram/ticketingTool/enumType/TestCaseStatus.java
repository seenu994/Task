package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum TestCaseStatus {

	DRAFT("DRAFT"),
	DEPRECATED("DEPRECATED"),
	APPROVED("APPROVED"),
	INREVIEW("INREVIEW"),
	REVISIT("REVISIT"),
	OUTOFSCOPE("OUTOFSCOPE"),
	NOTAPPLICABLE("NOTAPPLICABLE"),
	COMMENTSINCORPORATED("COMMENTSINCORPORATED");

	private String value;

	TestCaseStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static TestCaseStatus toEnum(String value) {
		Optional<TestCaseStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}
