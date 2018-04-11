package com.selfJwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Categories {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private @Getter @Setter String id;

	@NotEmpty(message="Name field mandatory")
	@JsonProperty
	@Column(unique = true)
	private @Getter @Setter String name;

	@JsonProperty
	@NotEmpty(message="DisplayName field mandatory")
	private @Getter @Setter String displayName;

	@JsonProperty
	@NotEmpty(message="ImageUrl field mandatory")
	private @Getter @Setter String imageUrl;

	private @Getter @Setter Date createdAt;

	private @Getter @Setter int active;
	
}
