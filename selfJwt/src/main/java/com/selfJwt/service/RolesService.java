package com.selfJwt.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfJwt.model.Privilege;
import com.selfJwt.model.Roles;
import com.selfJwt.repo.RolesRepo;

@Service
public class RolesService {

	@Autowired
	private RolesRepo repo;

	@Autowired
	private PrivilegesService privilegeService;

	public Roles create(Roles roles) {
		Roles save = repo.save(roles);
		return save;
	}

	public ArrayList<Roles> getAll() {
		Iterable<Roles> findAll = repo.findAll();
		ArrayList<Roles> allRoles = new ArrayList<Roles>();
		findAll.forEach(allRoles::add);
		return allRoles;
	}

	public Roles getById(String id) {
		Roles findOne = repo.findOne(id);
		return findOne;
	}

	public Roles getByName(String name) {
		Roles byRolesName = repo.findByRolesName(name);
		return byRolesName;
	}

	public void delete(String id) {
		repo.delete(id);
	}

	public Roles update(Roles roles) {
		Set<Privilege> all = privilegeService.getAll();
		roles.setPrivileges(all);
		Roles save = repo.save(roles);
		return save;
	}
}
