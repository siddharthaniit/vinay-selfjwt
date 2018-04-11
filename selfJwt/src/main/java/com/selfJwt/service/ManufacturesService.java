package com.selfJwt.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfJwt.Utils.CustomException;
import com.selfJwt.model.Categories;
import com.selfJwt.model.Manufacturers;
import com.selfJwt.repo.ManufacturesRepo;

@Service
public class ManufacturesService {

	@Autowired
	private ManufacturesRepo repo;
	
	@Autowired
	private CategoriesService categorieService;
	
	public Manufacturers create(Manufacturers manufacturers,String categorieId) throws CustomException {
		Manufacturers save = null;
		if(categorieId==null){
			throw new CustomException("Categorie cann't be null");
		}
		else {
			Categories findOne = categorieService.findById(categorieId);
			manufacturers.setCategories(findOne);
			save = repo.save(manufacturers);
		}
		return save;
	}
	
	public Set<Manufacturers> getAll() {
		Iterable<Manufacturers> findAll = repo.findAll();
		HashSet<Manufacturers> hashSet = new HashSet<Manufacturers>();
		findAll.forEach(hashSet::add);
		return hashSet;
	}
	
	public Manufacturers findById(String id) {
		Manufacturers findOne = repo.findOne(id);
		return findOne;
	}
	
	public void delete(String id) {
		repo.delete(id);
	}
	
	public Manufacturers updateById(Manufacturers manufacturers,String id,String categorieId) {
		Categories findOne = categorieService.findById(categorieId);
		manufacturers.setId(id);
		manufacturers.setCategories(findOne);
		Manufacturers save = repo.save(manufacturers);
		return save;
	}
	
	public Set<Manufacturers> getByCategorieId(String id) {
		Set<Manufacturers> findByCategoriesId = repo.findByCategoriesId(id);
		return findByCategoriesId;
	}
}
