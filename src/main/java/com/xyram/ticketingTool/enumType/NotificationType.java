package com.xyram.ticketingTool.enumType;

import java.util.Arrays;
import java.util.Optional;

public enum NotificationType {

	PROJECT_ASSIGN_ACCCES("PROJECTASSIGNACCCES"),
	PROJECT_ACCESS_REMOVE("PROJECTACCESSREMOVED"),
	TICKET_CREATED("TICKETCREATED"),
	TICKET_ASSIGNED("TICKETASSIGNED"),
	TICKET_INREVIEW("TICKETINREVIEW"),
	TICKET_RESOLVED("TICKETRESOLVED"),
	TICKET_CANCELLED_BY_ADMIN("TICKETCANCELLEDBYADMIN"),
	TICKET_CANCELLED_BY_USER("TICKETCANCELLEDBYUSER"),
	TICKET_REOPENED_BY_ADMIN("TICKETREOPENEDBYADMIN"),
	TICKET_REOPENED_BY_DEV("TICKETREOPENEDBYDEV"),
	TICKET_EDITED_BY_DEV("TICKETEDITEDBYDEV"),
	ATTACHMENT_ADDED_BY_ADMIN("ATTACHMENTADDEDBYADMIN"),
	ATTACHMENT_ADDED_BY_DEV("ATTACHMENTADDEDBYDEV"),
	ATTACHMENT_ADDED_BY_INFRA_USER("ATTACHMENTADDEDBYINFRAUSER"),
	COMMENTS_ADDED_BY_ADMIN("COMMENTSADDEDBYADMIN"),
	COMMENTS_ADDED_BY_DEV("COMMENTSADDEDBYDEV"),
	COMMENTS_ADDED_BY_INFRA_USER("COMMENTSADDEDBYINFRAUSER");

	private String value;

	NotificationType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static NotificationType toEnum(String value) {
		Optional<NotificationType> optional = Arrays.stream(values()).filter(e -> e.value.equalsIgnoreCase(value))
				.findFirst();
		if (optional.isPresent())
			return optional.get();
		else
			throw new IllegalArgumentException(value + " is not a valid status");
	}
}