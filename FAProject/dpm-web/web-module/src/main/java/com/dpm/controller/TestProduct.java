package com.dpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dpm.entity.Product;
import com.dpm.service.ProductService;

@Controller
@RequestMapping({ "/dacbiet" })
public class TestProduct {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
	    List<Product> listProducts = productService.listAll();
	    model.addAttribute("listProduct1s", listProducts);
	     
	    return "index";
	}
	
	@RequestMapping("/new")
	public String showNewProductPage(Model model) {
	    Product product = new Product();
	    model.addAttribute("produc1t", product);
	     
	    return "new-product";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("nameofproduct") Product product) {
	    productService.save(product);
	     
	    return "redirect:/";
	}
	
	
}
