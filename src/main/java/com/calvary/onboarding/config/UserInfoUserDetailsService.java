package com.calvary.onboarding.config;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calvary.onboarding.Dao.UserDao;
import com.calvary.onboarding.model.User;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userInfo = userDao.findByUserName(username);
		return userInfo.map(user -> user)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	}
}