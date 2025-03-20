package com.calvary.onboarding.config;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.calvary.onboarding.Dao.UserDao;
import com.calvary.onboarding.model.User;
import com.calvary.onboarding.utils.CommonsUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomSha256WithSaltPasswordEncoder implements AuthenticationProvider {
	@Autowired
	private UserDao userDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		Optional<User> user = userDao.findByUserName(username);
		boolean isValid = CommonsUtils.isPasswordValid(user.get().getPassword(), password, user.get().getSalt());
		if (!isValid) {
			log.info("invalid crediential");
			throw new BadCredentialsException("invalid crediential");
		}

		return new UsernamePasswordAuthenticationToken(username, user.get().getPassword(), user.get().getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}