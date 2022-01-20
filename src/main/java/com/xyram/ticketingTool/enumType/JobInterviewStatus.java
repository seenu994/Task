package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum JobInterviewStatus {


	SCHEDULED ("SCHEDULED"),

	SELECTED("SELECTED"),
	
	REJECTED("REJECTED"),
	
	CANDIDATE_NOT_ATTENDED("CANDIDATE_NOT_ATTENDED"),
	DEAFULT("DEAFULT"),
	INITIAED("INITIAED");
	
	
	
	private String value;

	JobInterviewStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	

	public static JobInterviewStatus toEnum(String value) {
		Optional<JobInterviewStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}
