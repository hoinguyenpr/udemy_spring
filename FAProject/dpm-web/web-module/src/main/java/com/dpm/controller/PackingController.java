package com.dpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpm.entity.Packing;
import com.dpm.service.impl.PackingServiceImpl;

/**
 * 
 * @author VuDH11
 *
 */
@Controller
@RequestMapping("/packing")
public class PackingController {
	
	@Autowired
	PackingServiceImpl packingService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getall(Model model) {
		List<Packing> packList = packingService.listAll();
		
		model.addAttribute("packList", packList);
		model.addAttribute("pack", new Packing());
		
		return "packing";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePacking(@ModelAttribute Packing packing) {
		if(packing.getId() == null) {
			
			packingService.addPacking(packing);
		}else {
			
			Packing updated = packingService.findById(packing.getId());
			updated.setPackingCode(packing.getPackingCode());
			updated.setPackingName(packing.getPackingName());
			packingService.addPacking(updated);
		}
		return "redirect:/packing/";
	}
	
	@RequestMapping(value ="/delete", method = RequestMethod.GET)
	public String deletePacking(@RequestParam("id") Integer id) {
		packingService.deletePacking(id);
		return "redirect:/packing/";
	}
	
	@RequestMapping(value="/packingBy", method = RequestMethod.POST)
	@ResponseBody public Packing getPackingById(@RequestBody Integer id) {
		return packingService.findById(id);
	}
	
	
}
