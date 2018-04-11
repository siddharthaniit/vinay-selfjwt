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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Roles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private @Getter @Setter String id;

	@Column(unique = true)
	@NotEmpty(message = "RoleName is mandatory")
	private @Getter @Setter String rolesName;

	@ManyToMany()
	@JoinTable(name = "roles_privileges", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id"),
			@JoinColumn(name = "roles_name", referencedColumnName = "rolesName") }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id", referencedColumnName = "id"),
					@JoinColumn(name = "privilege_name", referencedColumnName = "name") })
	private @Getter @Setter Set<Privilege> privileges;

}
