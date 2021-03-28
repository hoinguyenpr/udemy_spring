package com.dpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpm.entity.Department;
import com.dpm.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// modify by DinhDN
	/**
	 * Function create or update department.
	 * 
	 * @param department
	 * @param bindingResult
	 * @param model
	 * @return view.
	 */
	@PostMapping("/addDepartment")
	public String addDepartment(@ModelAttribute("department") Department department,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/department/";
		}

		departmentService.saveOrUpdate(department);
		return "redirect:/department/";
	}

	@PostMapping("/delete")
	public String deleteDepartment(@RequestParam("deleteId") String departmentCode) {
		System.out.println(departmentCode);
		try {
			departmentService.deleteDepartment(departmentCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/department/";
	}

	@GetMapping(value = { "/", "search", "departmentCode" })
	public String getAllListDepartment(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@Param("search") String search) {

		System.out.println(search);
		Sort sortable = null;

		Page<Department> list = null;
		try {
			if (sort.equals("ASC")) {
				sortable = Sort.by("departmentCode").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("departmentCode").descending();
			}
			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			if (search != null) {
				list = departmentService.listAllForSearch(pageable, search);
				System.out.println(list);

			} else {
				list = departmentService.ListAllPaging(pageable);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println(list);

		System.out.println(size);

		model.addAttribute("department", new Department());
		model.addAttribute("departments", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("totalItems", list.getTotalElements());
		model.addAttribute("pageSize", size);

		return "dept-management";

	}
}