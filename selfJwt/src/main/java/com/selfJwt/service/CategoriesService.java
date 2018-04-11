package com.selfJwt.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfJwt.model.Categories;
import com.selfJwt.repo.CategoriesRepo;

/**
 * @author vinayr
 *
 */
@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepo repo;

	public Categories create(Categories categories) {
		categories.setCreatedAt(new Date());
		categories.setActive(1);
		Categories save = repo.save(categories);
		return save;
	}

	public Set<Categories> getAll() {
		Iterable<Categories> findAll = repo.findAll();
		HashSet<Categories> hashSet = new HashSet<Categories>();
		findAll.forEach(hashSet::add);
		return hashSet;
	}

	public Categories findById(String id) {
		Categories findOne = repo.findOne(id);
		return findOne;
	}

	public Categories update(Categories categories, String id) {
		categories.setId(id);
		Categories save = repo.save(categories);
		return save;
	}

	public void deleteById(String id) {
		repo.delete(id);
	}
}
