package com.upendra.portfolio.port.dto.response;

import lombok.Data;

@Data
public class ContactStatsResponse {

    private Long totalMessages;

    private Long unreadMessages;

}