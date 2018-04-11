package com.selfJwt.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private @Getter @Setter String id;

	@Column(unique = true)
	@Email(message="provide valid email address")
	@NotEmpty(message = "email is mandatory")
	private @Getter @Setter String email;

	@NotNull(message = "age is mandatory")
	private @Getter @Setter int age;

	@NotEmpty(message = "name is mandatory")
	private @Getter @Setter String name;

	@ManyToMany()
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id"),
			@JoinColumn(name = "user_email", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id"),
					@JoinColumn(name = "role_name", referencedColumnName = "rolesName") })
	private @Getter @Setter Set<Roles> roles;

	@NotEmpty(message = "password is mandatory")
	private String password;

	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
