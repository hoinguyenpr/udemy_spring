package com.dpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpm.entity.FinishedProduct;
import com.dpm.service.FinishedProductService;

@RequestMapping("/finished-product")
@Controller
public class FinishedProductController {
	
	@Autowired
	public FinishedProductService finishedProductService;

	@GetMapping(value = { "/", "search", "ProductCode" })
	public String getAllProduct(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@RequestParam(name = "search", required = false) String search) {

		Sort sortable = null;
		Page<FinishedProduct> list = null;
		try {
			if (sort.equals("ASC")) {
				sortable = Sort.by("id").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("id").descending();
			}
			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			if (search != null) {
				list = finishedProductService.listAllForSearch(pageable, search);
			} else {
				list = finishedProductService.getListProductCodePaging(pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("finishedProducts", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("totalItems", list.getTotalElements());
		model.addAttribute("pageSize", size);
		model.addAttribute("finishedProduct", new FinishedProduct());

		return "finished-product";

	}

	@GetMapping(path = "/getAccountById/{productCode}")
	public FinishedProduct getTypeProductById(@PathVariable("productCode") String productCode) {
		FinishedProduct product = null;

		try {

			product = finishedProductService.getProductByProductCode(productCode);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return product;

	}

	@PostMapping("/addProduct")
	public String addProducts(@ModelAttribute("product") FinishedProduct product, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/";
		}
		System.out.println(product.getId());

		finishedProductService.saveOrUpdateProduct(product);

		return "redirect:/";
	}

	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String postProductsDelete(@RequestParam("deleteId") Integer id) {
		finishedProductService.deleteProduct(id);
		return "redirect:/";

	}

}
