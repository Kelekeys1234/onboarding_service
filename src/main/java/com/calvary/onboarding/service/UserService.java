package com.calvary.onboarding.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.calvary.onboarding.Dao.RegistrationPaymenDao;
import com.calvary.onboarding.Dao.RoleDao;
import com.calvary.onboarding.Dao.UserDao;
import com.calvary.onboarding.config.enun.Gender;
import com.calvary.onboarding.dto.AuthenticationDto;
import com.calvary.onboarding.dto.PasswordResest;
import com.calvary.onboarding.dto.SignUpDto;
import com.calvary.onboarding.dto.TokenDto;
import com.calvary.onboarding.exception.EmailAlreadyRegisteredException;
import com.calvary.onboarding.model.Name;
import com.calvary.onboarding.model.RegistrationPayment;
import com.calvary.onboarding.model.Role;
import com.calvary.onboarding.model.User;
import com.calvary.onboarding.utils.CommonsUtils;
import com.calvary.onboarding.utils.PasswordMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TokenGenerator tokenGenerator;
	@Autowired
	private RegistrationPaymenDao paymentDao;
	@Autowired
	private EmailService SMSService;
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${admin_role}")
	private String ADMIN_ROLE;

	private static SecureRandom random = new SecureRandom();;

	public SignUpDto signUpUser(SignUpDto signUpDto) {
		log.info("Inside sign-up user service");

		if (ObjectUtils.isEmpty(signUpDto) || ObjectUtils.isEmpty(signUpDto.getName())) {
			log.error("Sign-up request is invalid");
			throw new IllegalArgumentException("Sign-up details cannot be empty");
		}
		String[] nameParts = splitName(signUpDto.getName());
		String firstName = nameParts[0];
		String lastName = nameParts[1];
		// Generate random password
		String generatedPassword = generateRandomPassword(7);
		// Check if email, name, or phone number already exists
		if (userDao.existsByEmail(signUpDto.getEmail())) {
			log.error("Email already exists: {}", signUpDto.getEmail());
			throw new EmailAlreadyRegisteredException("Email is already registered");
		}
		if (userDao.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
			log.error("Phone number already exists: {}", signUpDto.getPhoneNumber());
			throw new EmailAlreadyRegisteredException("Phone number is already registered");
		}

		String salt = PasswordMapper.generateSalt();
		String hashedPassword = PasswordMapper.hashPassword(generatedPassword, salt);

		// Manual Mapping from DTO to Entity
		User userEntity = new User();
		userEntity.setId(UUID.randomUUID());
		Name name = new Name();
		name.setFirstName(firstName);
		name.setLastName(lastName);
		
		userEntity.setName(name);
		userEntity.setPhoneNumber(signUpDto.getPhoneNumber());
		userEntity.setSalt(salt);
		userEntity.setEmail(signUpDto.getEmail());
		userEntity.setGeneratedPassword(hashedPassword);
		userEntity.setCountry(signUpDto.getCountry());
		if (Gender.MALE.name().equalsIgnoreCase(signUpDto.getGender())) {
			userEntity.setGender(Gender.MALE.name());
		} else {
			userEntity.setGender(Gender.FEMALE.name());
		}
		userEntity.setCreatedBy(userEntity.getId().toString());
		userEntity.setCreatedDate(LocalDateTime.now());
		if (!ObjectUtils.isEmpty(signUpDto.getEmail())) {
			SMSService.sendWelcomeEmail(signUpDto.getEmail(), firstName, lastName, generatedPassword);
		} else if (!ObjectUtils.isEmpty(signUpDto.getPhoneNumber())) {
			log.info("phone number verification is yet to proceed..");
		}
		User savedUser = userDao.saveUpdateUser(userEntity);
		RegistrationPayment payment = new RegistrationPayment();
		payment.setId(UUID.randomUUID());
		payment.setAmount(signUpDto.getDonation());
		payment.setCurrency(signUpDto.getCurrency());
		payment.setCreatedBy(userEntity.getId().toString());
		payment.setCreatedDate(new Date());
		payment.setUser(savedUser);
		RegistrationPayment savePayment = paymentDao.savePayment(payment);
		

		// Manual Mapping from Entity back to DTO
		SignUpDto responseDto = new SignUpDto();
		responseDto.setId(savedUser.getId().toString());
		responseDto.setName(savedUser.getName().getFirstName() + " " + savedUser.getName().getLastName());
		responseDto.setPhoneNumber(savedUser.getPhoneNumber());
		responseDto.setEmail(savedUser.getEmail());
		responseDto.setCountry(savedUser.getCountry());
		responseDto.setGender(savedUser.getGender());
		responseDto.setCurrency(savePayment.getCurrency());
		responseDto.setDonation(savePayment.getAmount());
		log.info("User signed up successfully with ID: {}", savedUser.getId());
		return responseDto;
	}

	public static String[] splitName(String fullName) {
		if (fullName == null || fullName.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		// Split into at most 2 parts (firstName & lastName)
		String[] parts = fullName.trim().split("\\s+", 2);
		String firstName = parts[0];
		String lastName = parts.length > 1 ? parts[1] : ""; // Handle case with only one name

		return new String[] { firstName, lastName };
	}

	// Method to generate a random password
	public static String generateRandomPassword(int length) {
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*!";
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}

		return password.toString();
	}

	public String firstTimePassword(PasswordResest authDto) {
		log.info("inside register of first time password ..");
		// checking if username exists ..
		log.info("userName can be email or phone number ");
		Optional<User> userOptional = userDao.findByUserName(authDto.getUserName());
		if (ObjectUtils.isEmpty(userOptional)) {
			log.error("user details not found: {}", authDto.getUserName());
			throw new IllegalArgumentException("user phone number or email not found ...");
		}
		log.info("user details is present in database");
		User user = userOptional.get();

		Role roleFrmDb = roleDao.findByRoleName(ADMIN_ROLE);
		String salt = PasswordMapper.generateSalt();

		if (ObjectUtils.isEmpty(roleFrmDb)) {
			Role role = new Role();
			role.setId(UUID.randomUUID());
			role.setName(ADMIN_ROLE);
			role.setIsActive(true);
			role.setCreatedDate(LocalDateTime.now());
			role.setCreatedBy(user.getId().toString());
			Role saveRole = roleDao.saveRole(role);
			user.setRoles(Collections.singleton(saveRole));
		} else {
			user.setRoles(Collections.singleton(roleFrmDb));
		}

		boolean isPasswordValid = CommonsUtils.isPasswordValid(user.getGeneratedPassword(), authDto.getGeneratedPassword(),
				user.getSalt());
		if (!isPasswordValid) {
			log.info("password and the one time password generated do not match");
			throw new BadCredentialsException("password and the one time password generated do not match");
		}
		user.setSalt(salt);
		user.setPassword(PasswordMapper.hashPassword(authDto.getNewPassword(), salt));
		userDao.saveUpdateUser(user);
		return "Thank you for setting up your first time password you can now proceed to resest password of you choice if you want to ";
	}

	public TokenDto login(AuthenticationDto authDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUserName(), authDto.getPassword()));
		if (!authentication.isAuthenticated()) {
			log.info("invalid user request !");
			throw new UsernameNotFoundException("invalid user request !");
		}
		TokenDto tokenDto = new TokenDto();
		tokenDto.setAccessToken(tokenGenerator.createAccessToken(authentication));
		tokenDto.setRefreshToken(tokenGenerator.createRefreshToken(authentication));
		tokenDto.setExpiresIn(tokenGenerator.calculateAccessTokenExpiration(Instant.now()));
		tokenDto.setRefreshExpiresIn((tokenGenerator.calculateRefreshTokenExpiration(Instant.now())));
		return tokenDto;
	}

}
