package com.upendra.portfolio.contact.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.contact.client.EmailClient;
import com.upendra.portfolio.contact.dto.request.CreateContactRequest;
import com.upendra.portfolio.contact.dto.response.ContactResponse;
import com.upendra.portfolio.contact.entity.ContactMessage;
import com.upendra.portfolio.contact.repository.ContactRepository;
import com.upendra.portfolio.contact.service.ContactService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {


		private final ContactRepository repository;
		
		private final EmailClient emailClient;

		@Value("${portfolio.owner.email}")
		private String ownerEmailAddress;

		@Override
		public ContactResponse create(CreateContactRequest request) {

		    ContactMessage contact = ContactMessage.builder()
		            .name(request.getName())
		            .email(request.getEmail())
		            .subject(request.getSubject())
		            .message(request.getMessage())
		            .createdAt(LocalDateTime.now())
		            .updatedAt(LocalDateTime.now())
		            .build();

		    ContactMessage saved = repository.save(contact);

		    // ============================
		    // 1. Send notification to YOU
		    // ============================

		    String ownerBody = """
		            New contact form submission

		            Name: %s
		            Email: %s
		            Subject: %s

		            Message:
		            %s
		            """
		            .formatted(
		                    saved.getName(),
		                    saved.getEmail(),
		                    saved.getSubject(),
		                    saved.getMessage());

		    EmailRequest ownerEmail = EmailRequest.builder()
		            .to(ownerEmailAddress) // @Value("${portfolio.owner.email}")
		            .subject("New Portfolio Contact")
		            .body(ownerBody)
		            .build();

		    emailClient.sendEmail(ownerEmail);

		    // ============================
		    // 2. Auto reply to visitor
		    // ============================

		    String replyBody = """
		            Hello %s,

		            Thank you for contacting me.

		            I have received your message and will get back to you as soon as possible.

		            Regards,
		            Upendra
		            """
		            .formatted(saved.getName());

		    EmailRequest autoReply = EmailRequest.builder()
		            .to(saved.getEmail())
		            .subject("Thank you for contacting me")
		            .body(replyBody)
		            .build();

		    emailClient.sendEmail(autoReply);

		    return map(saved);
		}



			@Override
			public List<ContactResponse> getAll(){

				return repository.findAllByOrderByCreatedAtDesc()
						.stream()
						.map(this::map)
						.toList();

			}



			@Override
			public ContactResponse markAsRead(Long id){


				ContactMessage contact=
										repository.findById(id)
													.orElseThrow(
																()->new RuntimeException("Message not found"));


					contact.setReadStatus(true);

					return map(repository.save(contact));

			}



			@Override
			public void delete(Long id){

				repository.deleteById(id);

			}



			private ContactResponse map(ContactMessage c){

					return ContactResponse.builder()
							.id(c.getId())
							.name(c.getName())
							.email(c.getEmail())
							.subject(c.getSubject())
							.message(c.getMessage())
							.readStatus(c.getReadStatus())
							.createdAt(c.getCreatedAt())
							.updatedAt(c.getUpdatedAt())
							.build();

			}



}