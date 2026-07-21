package com.upendra.portfolio.contact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.contact.dto.response.ContactStatsResponse;
import com.upendra.portfolio.contact.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contact/internal")
@RequiredArgsConstructor
public class ContactInternalController {


private final ContactRepository contactRepository;


	@GetMapping("/stats")
	public ContactStatsResponse stats(){


		return ContactStatsResponse.builder()

									.totalMessages(
												contactRepository.count()
											)

									.unreadMessages(
											contactRepository.countByReadStatusFalse()
											)

									.build();

	}

}