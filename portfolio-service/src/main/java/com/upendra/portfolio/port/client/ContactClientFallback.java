package com.upendra.portfolio.port.client;

import org.springframework.stereotype.Component;

import com.upendra.portfolio.port.dto.response.ContactStatsResponse;

@Component
public class ContactClientFallback 
        implements ContactClient {


    @Override
    public ContactStatsResponse getContactStats() {

        ContactStatsResponse response =
                new ContactStatsResponse();

        response.setTotalMessages(0L);
        response.setUnreadMessages(0L);

        return response;
    }
}