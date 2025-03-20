package com.calvary.onboarding.Dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.calvary.onboarding.model.Role;
import com.calvary.onboarding.repository.RoleRepository;

@Service
public class  RoleDao {
	@Autowired
	private RoleRepository roleRepository;

	
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	public Role findByRoleName(String name) {
		return roleRepository.findByName(name);
	}

}
