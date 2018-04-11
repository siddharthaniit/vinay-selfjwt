package com.selfJwt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selfJwt.model.Roles;
import com.selfJwt.service.RolesService;

@RestController
@RequestMapping(value = "roles")
public class RolesController {

	@Autowired
	private RolesService service;

	@PostMapping(value = "create")
	public Roles create(@RequestBody Roles roles) {
		Roles create = service.create(roles);
		return create;
	}

	@GetMapping(value = "getAll")
	public ArrayList<Roles> getAll() {
		ArrayList<Roles> all = service.getAll();
		return all;
	}

	@PutMapping(value = "update")
	public Roles updateRole(@RequestParam("id") String id, @RequestBody Roles roles) {
		roles.setId(id);
		Roles update = service.update(roles);
		return update;
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") String id) {
		service.delete(id);
	}
}
