package com.example.demo.apiController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PartDetail;
import com.example.demo.partService.PartService;

@RestController
@RequestMapping("/partApi")
public class ApiController {

	@Autowired
	private PartService partService;
	
	@GetMapping("/getAll")
	public List<PartDetail> getAllPart() {
		List<PartDetail> allPartList = new ArrayList<PartDetail>();
		allPartList = partService.getAllPart();
		return allPartList;
	}
	
	@GetMapping("/get/{partNumber}")
	public Object findByPartNumber(@PathVariable String partNumber ,Model model) {
		PartDetail part;
		try {
			part = partService.getPartByNumber(partNumber);
			if (part == null) return new ResponseEntity<>("Null part", HttpStatus.EXPECTATION_FAILED);
			return new ResponseEntity<>(part, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Part not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/add")
	public String addPart(@RequestBody PartDetail newPart) {
		String addResult = partService.savePart(newPart);
		return addResult;
	}
	
	@PutMapping("/add")
	public String updatePart(@RequestBody PartDetail newPart) {
		String addResult = partService.updatePart(newPart);
		return addResult;
	}
	
	@PutMapping("/obsolete")
	public String obsoletePart(@RequestBody PartDetail newPart) {
		String addResult = partService.obsoletePart(newPart.getPartNumber());
		return addResult;
	}
	
}
