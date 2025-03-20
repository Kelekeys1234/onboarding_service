package com.calvary.onboarding.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.calvary.onboarding.model.Role;


@Repository
public interface RoleRepository extends MongoRepository<Role, UUID> {
	Role findByName(String roleName);
}
