package com.dpm.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.MixingMaterial;
import com.dpm.utility.LocalDateUtil;
/**
   Update: Nguyennd6      Create date: 01/11/2020 16:00 AM          
 **/
@Repository
public interface MixingMaterialDAO extends JpaRepository<MixingMaterial, Integer> {
	

	List<MixingMaterial> findByIngredientBatchIngredientBatchNameContainingIgnoreCaseOrEmployeeEmployeeNameContainingIgnoreCase
	(String ingredientName, String employeeName);
	
	default List<MixingMaterial> findByIngredientBatchNameOrEmployeeName(String name) {
		return findByIngredientBatchIngredientBatchNameContainingIgnoreCaseOrEmployeeEmployeeNameContainingIgnoreCase(name,name);
	}
	
	List<MixingMaterial> findByTypeProductIdAndDateMixingAndMachineId(Integer productName, LocalDate date, Integer machine);
	
	default List<MixingMaterial> findByTypeProductAndDateAndMachine(String productName, String date, String machine) {
		
		return findByTypeProductIdAndDateMixingAndMachineId( Integer.parseInt(productName) ,LocalDateUtil.parse(date),Integer.parseInt(machine));
	}
	
	
	  List<MixingMaterial> findAll(Sort sort);
	  List<MixingMaterial> findAll();
	

}
