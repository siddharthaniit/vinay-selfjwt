package com.selfJwt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfJwt.model.User;
import com.selfJwt.service.UserService;

@RestController()
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(value = "register")
	public User register(@RequestBody User user) {
		User create = service.register(user);
		return create;
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "getAll")
	public ArrayList<User> getAll() {
		ArrayList<User> all = service.getAll();
		return all;
	}

	@RequestMapping(value = "create")
	@PreAuthorize("hasAuthority('ADMIN')")
	public User create(@RequestBody User user) {
		User create = service.create(user);
		return create;
	}

}
