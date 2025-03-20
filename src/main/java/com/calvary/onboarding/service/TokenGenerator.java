package com.calvary.onboarding.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.calvary.onboarding.Dao.UserDao;
import com.calvary.onboarding.model.User;

@Component
public class TokenGenerator {
	@Autowired
	JwtEncoder accessTokenEncoder;

	@Autowired
	private UserDao userDao;

	@Value("${access.token.expiration.minutes}")
	private int accessTokenExpirationMinutes;

	@Value("${refresh.token.expiration.minutes}")
	private int refreshTokenExpirationMinutes;

	@Autowired
	@Qualifier("jwtRefreshTokenEncoder")
	JwtEncoder refreshTokenEncoder;

	public String createAccessToken(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = userDao.findByUserName(authentication.getName()).get();
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuer("Onboarding Service").issuedAt(now)
				.expiresAt(Instant.ofEpochMilli(calculateAccessTokenExpiration(now).longValue())).claim("roles", scope)
				.subject(user.getPhoneNumber()).build();

		return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
	}

	public String createRefreshToken(Authentication authentication) {
		User user = userDao.findByUserName(authentication.getName()).get();
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuer("Onboarding Service").issuedAt(now)
				.expiresAt(Instant.ofEpochMilli(calculateAccessTokenExpiration(now).longValue())).claim("roles", scope)
				.subject(user.getPhoneNumber()).build();

		return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
	}

	public BigDecimal calculateAccessTokenExpiration(Instant issuedAt) {
		// Set the access token expiration time to, for example, 1 hour, represented as
		// BigDecimal
		Instant expirationTime = issuedAt.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES);
		return BigDecimal.valueOf(expirationTime.toEpochMilli());
	}

	public BigDecimal calculateRefreshTokenExpiration(Instant issuedAt) {
		// Set the refresh token expiration time to, for example, 30 days, represented
		// as BigDecimal
		Instant refreshTokenExpiration = issuedAt.plus(refreshTokenExpirationMinutes, ChronoUnit.MINUTES);
		return BigDecimal.valueOf(refreshTokenExpiration.toEpochMilli());
	}

}