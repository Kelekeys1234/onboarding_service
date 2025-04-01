package com.calvary.onboarding.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calvary.onboarding.config.GenericResponseHandlers;
import com.calvary.onboarding.dto.AuthenticationDto;
import com.calvary.onboarding.dto.PasswordResest;
import com.calvary.onboarding.dto.SignUpDto;
import com.calvary.onboarding.dto.TokenDto;
import com.calvary.onboarding.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/onboarding/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registration(@RequestBody SignUpDto signUpDto) {
		if (ObjectUtils.isEmpty(signUpDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return new GenericResponseHandlers.Builder()
	            .setData(userService.signUpUser(signUpDto))
	            .setStatus(HttpStatus.OK)
	            .setMessage("User registered successfully")
	            .create();

	}

	@PutMapping(value = "/user/first-time-Password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> firstTimePassword(PasswordResest authDto) {
		if (ObjectUtils.isEmpty(authDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return new GenericResponseHandlers.Builder()
	            .setData(userService.firstTimePassword(authDto))
	            .setStatus(HttpStatus.OK)
	            .setMessage("User registered successfully")
	            .create();

	}

	@PostMapping(value = "/user/signin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> signIn(AuthenticationDto authDto) {
		if (ObjectUtils.isEmpty(authDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return new GenericResponseHandlers.Builder()
	            .setData(userService.login(authDto))
	            .setStatus(HttpStatus.OK)
	            .setMessage("User registered successfully")
	            .create();

	}

}
