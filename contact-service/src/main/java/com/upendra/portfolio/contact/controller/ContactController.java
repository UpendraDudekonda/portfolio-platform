package com.upendra.portfolio.contact.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.contact.dto.request.CreateContactRequest;
import com.upendra.portfolio.contact.dto.response.ContactResponse;
import com.upendra.portfolio.contact.service.ContactService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {


    private final ContactService contactService;



    @PostMapping
    public ApiResponse<ContactResponse> createContact(
            @Valid @RequestBody CreateContactRequest request) {


        return ApiResponse.<ContactResponse>builder()
                .success(true)
                .message("Message sent successfully.")
                .data(contactService.create(request))
                .build();
    }



    @GetMapping
    public ApiResponse<List<ContactResponse>> getAllContacts() {


        return ApiResponse.<List<ContactResponse>>builder()
                .success(true)
                .message("Messages fetched successfully.")
                .data(contactService.getAll())
                .build();
    }



    @PutMapping("/{id}/read")
    public ApiResponse<ContactResponse> markAsRead(
            @PathVariable Long id) {


        return ApiResponse.<ContactResponse>builder()
                .success(true)
                .message("Message marked as read successfully.")
                .data(contactService.markAsRead(id))
                .build();
    }



    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContact(
            @PathVariable Long id) {


        contactService.delete(id);


        return ApiResponse.<Void>builder()
                .success(true)
                .message("Message deleted successfully.")
                .build();
    }

}
