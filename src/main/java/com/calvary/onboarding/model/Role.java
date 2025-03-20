package com.calvary.onboarding.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "roles")
public class Role {

	private UUID id;

	private String name;

	private Boolean isActive;

	private String description;

	private LocalDateTime createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

//	private Set<Permission> permissions;

}
