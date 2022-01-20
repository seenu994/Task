package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum JobApplicationStatus {
	SUBMITTED ("SUBMITTED"),
	APPLIED("APPLIED"),
	REJECTED_BY_HR ("REJECTED_BY_HR"),
	IN_REVIEW("IN_REVIEW"),
	SCHEDULED("SCHEDULED"),
	SELECTED ("SELECTED"),
	REJECTED_BY_INTERVIEWER("REJECTED_BY_INTERVIEWER"),
	ONHOLD("ONHOLD"),
	CANDIDATE_NOT_INTERESTED("CANDIDATE_NOT_INTERESTED"),
	RELEASED_OFFER("RELEASED_OFFER"),
	DEFAULT("DEFAULT");

	
	
	
	private String value;
	
	JobApplicationStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	
	public static JobApplicationStatus toEnum(String value) {
		Optional<JobApplicationStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
	
	}
