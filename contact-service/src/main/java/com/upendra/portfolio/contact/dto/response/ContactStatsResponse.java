package com.upendra.portfolio.contact.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactStatsResponse {

	 private Long totalMessages;

	    private Long unreadMessages;
}
