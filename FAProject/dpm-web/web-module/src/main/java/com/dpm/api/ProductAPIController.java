package com.dpm.api;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpm.dao.ProductDAO;

@RestController
@RequestMapping("/product")
public class ProductAPIController {

	@Autowired
	ProductDAO pro;

	@GetMapping(path = "/get-produc", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean getProduct(@RequestParam String productCode) {
		Boolean result = false;

		return pro.existsByProductCode(productCode);
	}

	@GetMapping(path = "/check-date", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean validateJavaDate(@RequestParam String strDate) {
		try {
			LocalDate date = LocalDate.parse(strDate);
		} catch (Exception e) {
			System.out.println("Invalid datetime: " + e.getMessage());
			return false;
		}
		return true;
	}
}
