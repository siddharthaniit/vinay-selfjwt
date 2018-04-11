package com.selfJwt.repo;

import org.springframework.data.repository.CrudRepository;

import com.selfJwt.model.User;

public interface UserRepo extends CrudRepository<User, String> {
	public User findByEmail(String email);
}
