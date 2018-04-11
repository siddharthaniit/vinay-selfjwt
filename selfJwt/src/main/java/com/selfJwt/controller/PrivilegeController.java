package com.selfJwt.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.selfJwt.model.Privilege;
import com.selfJwt.service.PrivilegesService;

@RestController
@RequestMapping(value = "privilege")
public class PrivilegeController {

	@Autowired
	private PrivilegesService service;
	
	@PostMapping(value = "create")
	public Privilege create(@RequestBody Privilege privilege) {
		Privilege create = service.create(privilege);
		return create;
	}

	@GetMapping(value = "getAll")
	@PreAuthorize("hasPermission('hasAccess','WRITE') and hasAuthority('ADMIN')")
	public Set<Privilege> getAll() {
		Set<Privilege> all = service.getAll();
		return all;
	}

	@PutMapping(value = "update")
	public Privilege Update(@RequestParam("id") String id, @RequestBody Privilege privilege) {
		privilege.setId(id);
		Privilege create = service.create(privilege);
		return create;
	}

	@DeleteMapping(value = "delete/{id}")
	public void delete(@PathVariable("id") String id) {
		service.delete(id);
	}
	
	@GetMapping(value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Privilege getById(@PathVariable("id") String id) {
		Privilege byId = service.getById(id);
		return byId;
	}
}
