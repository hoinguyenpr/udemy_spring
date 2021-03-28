package com.dpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dpm.service.IngredientBatchService;
@Controller
public class IngredientBatchController {
	
	@Autowired
	public IngredientBatchService ingredientBatchService;
	
	@RequestMapping("/ingredientBatch")
	public String ingredientBatch() {
		
		
		return "ingredientBatch";
		
	}
	@RequestMapping("/ingredientBatch/delete/{id}")
	public String ingredientBatchDelete(Model model,@PathVariable(value="id") Integer id) {
		
		ingredientBatchService.deleteIBById(id);
		return "redirect:ingredientBatch";
		
	}
}
