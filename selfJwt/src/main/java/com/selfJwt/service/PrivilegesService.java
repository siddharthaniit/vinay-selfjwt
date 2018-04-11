package com.selfJwt.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.selfJwt.model.Privilege;
import com.selfJwt.repo.PrivilegeRepo;

@Service
public class PrivilegesService {

	@Autowired
	private PrivilegeRepo repo;

	@Cacheable(value = "privilegeCache", key = "#result==null?#p0:result.id", unless = "#result==null")
	public Privilege create(Privilege privilege) {
		Privilege save = repo.save(privilege);
		return save;
	}

	@Cacheable(value = "privilegeCache")
	public Set<Privilege> getAll() {
		Iterable<Privilege> findAll = repo.findAll();
		Set<Privilege> arrayList = new HashSet<Privilege>();
		findAll.forEach(arrayList::add);
		return arrayList;
	}

	@Cacheable(value = "privilegeCache", key = "#id")
	public Privilege getById(String id) {
		Privilege findOne = repo.findOne(id);
		return findOne;
	}

	public void delete(String id) {
		repo.delete(id);
	}

	@Cacheable(value = "privilegeCache", key = "#name")
	public Privilege getByName(String name) {
		Privilege byName = repo.findByName(name);
		return byName;
	}
}
