package com.dpm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.IngredientBatch;

@Repository
public interface IngredientBatchDAO extends JpaRepository<IngredientBatch, Integer> {
// put stuff here
	
	// NguyenND6 20/01/2021 16:20 create findByName 
	List<IngredientBatch> findByIngredientBatchNameContainingIgnoreCase(String name);
	
}
