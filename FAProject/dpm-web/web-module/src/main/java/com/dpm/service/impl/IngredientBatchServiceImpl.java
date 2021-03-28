package com.dpm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpm.dao.IngredientBatchDAO;
import com.dpm.entity.IngredientBatch;
import com.dpm.service.IngredientBatchService;

@Service
public class IngredientBatchServiceImpl implements IngredientBatchService {

	@Autowired
	public IngredientBatchDAO ingredientBatchDao;

	// Add by LongVT7
	@Override
	public List<IngredientBatch> fetchAll() {
		return ingredientBatchDao.findAll();

	}

	@Override
	public void deleteIBById(Integer id) {
		// TODO Auto-generated method stub
		ingredientBatchDao.deleteById(id);
	}

	// TruongDD: added on 01-13-2021
	@Override
	public List<IngredientBatch> getAll() {

		try {
			List<IngredientBatch> batchList = ingredientBatchDao.findAll();
			return batchList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// TruongDD: added on 01-13-2021
	@Override
	public Optional<IngredientBatch> get(int id) {
		try {
			return ingredientBatchDao.findById(id);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
