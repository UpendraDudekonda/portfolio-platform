package com.upendra.portfolio.contact.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.contact.entity.ContactMessage;


public interface ContactRepository 
			extends JpaRepository<ContactMessage,Long>{


List<ContactMessage> findAllByOrderByCreatedAtDesc();

Long countByReadStatusFalse();


}