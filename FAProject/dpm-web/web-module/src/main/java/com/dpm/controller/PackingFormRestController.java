package com.dpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpm.entity.PackingForm;
import com.dpm.service.PackingFormService;

@RestController
@RequestMapping("packing-form/api")
public class PackingFormRestController {
	
	@Autowired
	PackingFormService packingFormService;
	
	@RequestMapping("/get-all")
	@GetMapping
	public List<PackingForm> getAll() {
		return packingFormService.getAllPackingForm();
	}
	
	
}
