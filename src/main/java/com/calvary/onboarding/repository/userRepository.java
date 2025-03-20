package com.calvary.onboarding.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.calvary.onboarding.model.User;

public interface userRepository extends MongoRepository<User, UUID> {

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByName_FirstNameAndName_LastName(String firstName, String lastName);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(String phoneNumber);

}
