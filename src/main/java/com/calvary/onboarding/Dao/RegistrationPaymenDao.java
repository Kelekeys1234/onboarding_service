package com.calvary.onboarding.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calvary.onboarding.model.RegistrationPayment;
import com.calvary.onboarding.repository.registrationPaymentRepository;

@Service
public class RegistrationPaymenDao {
	@Autowired
	private registrationPaymentRepository repository;
	
	public RegistrationPayment savePayment(RegistrationPayment registrationPayment) {
		return repository.save(registrationPayment);
	}

}
