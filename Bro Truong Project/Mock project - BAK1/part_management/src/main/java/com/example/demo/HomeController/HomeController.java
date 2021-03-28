package com.example.demo.HomeController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.entity.PartDetail;
import com.example.demo.partService.PartService;

@Controller
public class HomeController {
	
	@Autowired
	private PartService partService;
	
	@GetMapping("/")
	public String getHomePage() {
		return "home";
	}
	
	@GetMapping("/add-part")
	public String viewAddNewPart(Model model) {
		List<PartDetail> allPartList = partService.getAllActive();
		model.addAttribute("allPartList", allPartList);
		System.out.println(allPartList.toString());
		return ("new-part");
	}
	
	@PostMapping("/add-part")
	public String addNewPart(PartDetail newPart) {
		System.out.println(partService.savePart(newPart));
		return ("redirect:/add-part");
	}
	
	@PutMapping("/add-part")
	public String updatePart(PartDetail newPart) {
		PartDetail oldPart = new PartDetail();
		try {
			partService.getPartByNumber(newPart.getPartNumber());
			oldPart.setPartDesc(newPart.getPartDesc());
			partService.savePart(oldPart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("redirect:/add-part");
	}
	
	@PutMapping("/obsoletePart")
	public String obsoletePart(PartDetail part) {
		PartDetail oldPart = new PartDetail();
		try {
			oldPart = partService.getPartByNumber(part.getPartNumber());
			partService.obsoletePart(oldPart.getPartNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("redirect:/add-part");
	}
	
}
