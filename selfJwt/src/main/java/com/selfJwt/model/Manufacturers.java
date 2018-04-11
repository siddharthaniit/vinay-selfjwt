package com.selfJwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Manufacturers {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private @Getter @Setter String id;

	@JsonProperty
	@NotEmpty(message = "Name is mandatory")
	@Column(unique = true)
	private @Getter @Setter String name;

	@JsonProperty
	@NotEmpty(message = "DisplayName is mandatory")
	private @Getter @Setter String displayName;

	private @Getter @Setter Date createdAt;

	@JsonProperty
	@NotEmpty(message = "ImageUrl is mandatory")
	private @Getter @Setter String imageUrl;

	private @Getter @Setter int active;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categories_id")
	private @Getter @Setter Categories categories;

}
