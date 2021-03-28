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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpm.entity.Supplier;
import com.dpm.service.SupplierService;



@RequestMapping("/supplier")
@Controller
public class SupplierController {
	@Autowired
	public SupplierService supplierService;

//	@GetMapping("/")
//	public String supplier(Model model) {
//		List<Supplier> listSupplier = supplierService.listSupplier();
//		model.addAttribute("supplier", new Supplier());
//
//		model.addAttribute("suppliers", listSupplier);
//
//		return "ncc";
//
//	}

	@GetMapping(value = { "/", "search", "id" })
	public String getAllSupplier(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@RequestParam(name = "search", required = false) String search) {
		
		Sort sortable = null;
		Page<Supplier> list = null;
		try {
			if (sort.equals("ASC")) {
				sortable = Sort.by("id").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("id").descending();
			}
			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			if (search != null) {
				list = supplierService.listAllForSearch(pageable, search);
			} else {
				list = supplierService.getListSupplierCodePaging(pageable);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("suppliers", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("totalItems", list.getTotalElements());
		model.addAttribute("pageSize", size);
		model.addAttribute("supplier", new Supplier());

		return "ncc";

	}


	@PostMapping("/addSupplier")
	public String addDepartment(@ModelAttribute("supplier") Supplier supplier, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/supplier/";
		}
		System.out.println(supplier.getId());

		supplierService.saveOrUpdateSupplier(supplier);

		return "redirect:/supplier/";
	}

	@PostMapping("/delete")
	public String supplierDelete(@RequestParam("deleteId") Integer id) {

		supplierService.deleteSupplierById(id);
		return "redirect:/supplier/";
	}
}
