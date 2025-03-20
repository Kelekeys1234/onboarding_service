package com.calvary.onboarding.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "registration_payments")
public class RegistrationPayment {
    
    @Id
    private UUID id;
    private String currency;
    private double amount;
    private String otherAmount;
    @DBRef
    private User user;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
}