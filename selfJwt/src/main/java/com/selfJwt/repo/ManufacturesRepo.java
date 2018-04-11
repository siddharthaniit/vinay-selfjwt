package com.selfJwt.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.selfJwt.model.Manufacturers;

public interface ManufacturesRepo extends CrudRepository<Manufacturers, String> {
	Set<Manufacturers> findByCategoriesId(@Param("id") String id);
}
