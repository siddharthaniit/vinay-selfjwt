package com.selfJwt.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfJwt.model.Categories;
import com.selfJwt.service.CategoriesService;

@RestController
@RequestMapping(value="categories")
public class CategoriesController {

	@Autowired
	private CategoriesService service;
	
	@PostMapping(value="create")
	public Categories create(@RequestBody Categories categories) {
		Categories create = service.create(categories);
		return create;
	}
	
	@GetMapping(value="getall")
	public Set<Categories> getAll() {
		Set<Categories> all = service.getAll();
		return all;
	}
	
	@GetMapping(value="/{id}")
	public Categories findById(@PathVariable("id") String id) {
		Categories findById = service.findById(id);
		return findById;
	}
	
	@PutMapping(value="/update/{id}")
	public Categories update(@RequestBody Categories categories,@PathVariable("id") String id) {
		Categories update = service.update(categories, id);
		return update;
	}
	
	@DeleteMapping(value="/{id}")
	public void delete(@PathVariable("id") String id) {
		service.deleteById(id);
	}
}
