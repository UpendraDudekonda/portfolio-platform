package com.upendra.portfolio.contact.entity;


import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="contact_messages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String email;


    private String subject;


    @Column(length = 3000)
    private String message;



    @Builder.Default
    private Boolean readStatus=false;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


}