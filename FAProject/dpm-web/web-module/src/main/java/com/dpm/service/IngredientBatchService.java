package com.dpm.service;

import java.util.List;
import java.util.Optional;

import com.dpm.entity.IngredientBatch;

public interface IngredientBatchService {

	// Add by LongVT7
	List<IngredientBatch> fetchAll();

	public void deleteIBById(Integer id);

	public List<IngredientBatch> getAll();

	// Truongdd: added on 01-13-2021
	public Optional<IngredientBatch> get(int id);

}
