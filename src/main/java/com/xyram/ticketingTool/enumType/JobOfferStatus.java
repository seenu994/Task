package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum JobOfferStatus {
	
	OFFERED("OFFERED"),
	
	CANDIDATE_REJECTED("CANDIDATE_REJECTED"),
	
	CANDIDATE_NOT_ANSWERED("CANDIDATE_NOT_ANSWERED"),
	
	HR_REJECTED("HR_REJECTED");
	
	private String value;

	JobOfferStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	

	public static JobOfferStatus toEnum(String value) {
		Optional<JobOfferStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}

}
