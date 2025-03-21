package com.calvary.onboarding.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/onboarding/register")
	public ResponseEntity<SignUpDto> registration(@RequestBody SignUpDto signUpDto) {
		if (ObjectUtils.isEmpty(signUpDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUpUser(signUpDto));

	}

	@PutMapping("/user/first-time-Password")
	public ResponseEntity<String> firstTimePassword(PasswordResest authDto) {
		if (ObjectUtils.isEmpty(authDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.firstTimePassword(authDto));

	}

	@PostMapping("/user/signin")
	public ResponseEntity<TokenDto> signIn(AuthenticationDto authDto) {
		if (ObjectUtils.isEmpty(authDto)) {
			log.info("Sign-up request cannot be empty");
			throw new IllegalArgumentException("Sign-up request body is missing");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(authDto));

	}

}
