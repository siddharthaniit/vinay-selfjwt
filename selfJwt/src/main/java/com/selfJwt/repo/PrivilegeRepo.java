package com.selfJwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selfJwt.model.Privilege;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, String> {
	public Privilege findByName(String name);
}
