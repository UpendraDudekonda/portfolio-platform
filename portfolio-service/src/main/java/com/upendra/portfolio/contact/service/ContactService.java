package com.upendra.portfolio.contact.service;

import java.util.List;

import com.upendra.portfolio.contact.dto.request.CreateContactRequest;
import com.upendra.portfolio.contact.dto.response.ContactResponse;

public interface ContactService {

	ContactResponse create(
	        CreateContactRequest request);


	List<ContactResponse> getAll();


	ContactResponse markAsRead(Long id);


	void delete(Long id);
}
