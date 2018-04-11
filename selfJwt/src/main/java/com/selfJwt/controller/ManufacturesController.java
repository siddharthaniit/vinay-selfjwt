package com.selfJwt.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.selfJwt.Utils.CustomException;
import com.selfJwt.model.Manufacturers;
import com.selfJwt.service.ManufacturesService;

@RestController
@RequestMapping(value="manufacture")
public class ManufacturesController {

	@Autowired
	private ManufacturesService service;
	
	@RequestMapping(value="/{categorieid}",method=RequestMethod.POST)
	public Manufacturers create(@RequestBody Manufacturers manufacturers,@PathVariable("categorieid") String categorieId) throws CustomException {
		Manufacturers create = service.create(manufacturers,categorieId);
		return create;
	}
	
	@RequestMapping(value="getall",method=RequestMethod.GET)
	public Set<Manufacturers> getAll() {
		Set<Manufacturers> all = service.getAll();
		return all;
	}
	
	@RequestMapping(value="/{id}")
	public Manufacturers findById(@PathVariable("id") String id) {
		Manufacturers findById = service.findById(id);
		return findById;
	}
	
	@RequestMapping(value="/{id}/Categorie/{categorieid}",method=RequestMethod.PUT)
	public Manufacturers update(@PathVariable("id") String manufactureId,@PathVariable("categorieid") String categorieId,@RequestBody Manufacturers manufacturers) {
		Manufacturers updateById = service.updateById(manufacturers, manufactureId, manufactureId);
		return updateById;
	}
	@RequestMapping(value="/categories/{id}",method=RequestMethod.GET)
	public Set<Manufacturers> findByCategorieId(@PathVariable("id") String categoriesId) {
		Set<Manufacturers> byCategorieId = service.getByCategorieId(categoriesId);
		return byCategorieId;
	}
}
