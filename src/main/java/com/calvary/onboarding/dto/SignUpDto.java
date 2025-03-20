package com.calvary.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpDto {

	@JsonProperty("user_id")
	private String id;
	
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

	@Email(message = "Invalid email format")
	private String email;

	private String address;

	@NotBlank(message = "Country is required")
	private String country;

	private String gender;

	@NotBlank(message = "Currency is required")
	private String currency;

	@NotBlank(message = "Donation amount is required")
	private double donation;
}
