package com.calvary.onboarding.Dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calvary.onboarding.model.User;

@Service
public class UserDao {

	@Autowired
	private com.calvary.onboarding.repository.userRepository userRepository;

	public User saveUpdateUser(User user) {
		return userRepository.save(user);

	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean existsByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

	public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
		return userRepository.existsByName_FirstNameAndName_LastName(firstName, lastName);
	}

	 public Optional<User> findByUserName(String username) {
	        if (isValidEmail(username)) {
	            return userRepository.findByEmail(username);
	        } else if (isValidPhoneNumber(username)) {
	            return userRepository.findByPhoneNumber(username);
	        } else {
	            return Optional.empty(); // Invalid format
	        }
	    }

	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email.matches(emailRegex);
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		String phoneRegex = "^[0-9]{10,15}$"; // Allows 10-15 digits
		return phoneNumber.matches(phoneRegex);
	}
}
