package com.calvary.onboarding.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.calvary.onboarding.model.RegistrationPayment;

public interface registrationPaymentRepository extends MongoRepository<RegistrationPayment, UUID> {

}
