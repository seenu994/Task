package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum AssetEmployeeStatus {
	
	REPAIR("REPAIR"),
	EXCHANGE("EXCHANGE"),
	RESIGNED("RESIGNED"),
	RETURN("RETURN"),
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE");
	private String value;

	
	AssetEmployeeStatus(String value) {
		this.value = value;
	}

  
	
	public String value() {
		return this.value;
	}
	
	public static AssetEmployeeStatus toEnum(String value) {
		Optional<AssetEmployeeStatus> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"not valid status");
	}

		
	
}
