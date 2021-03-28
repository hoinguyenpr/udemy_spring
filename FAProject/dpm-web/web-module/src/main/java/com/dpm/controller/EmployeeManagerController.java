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

import com.dpm.entity.Employee;
import com.dpm.service.EmployeeService;

@Controller
@RequestMapping("/employeemanager")
public class EmployeeManagerController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@PostMapping("/addEmployee")
	public String addDepartment(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/employeemanager/";
	    } 

		employeeService.saveOrUpdateEmployee(employee);
		return "redirect:/employeemanager/";
	}
	
	@PostMapping("/delete")
	public String deleteDepartment(@RequestParam("deleteId") Integer id) {
		System.out.println(id);
		try {
			employeeService.deleteEmployee(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/employeemanager/";
	}
	
	@GetMapping(value = { "/", "search", "id" })
	public String getAllListDepartment(Model model,
			@RequestParam(name = "page", 	required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", 	required = false, defaultValue = "10") Integer size,
			@RequestParam(name = "sort", 	required = false, defaultValue = "ASC") String sort,
			@Param("search") String search) {
		
		
		System.out.println(search);
		Sort sortable = null;
		
		Page<Employee> list = null;
		try {
			if (sort.equals("ASC")) {
				sortable = Sort.by("id").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("id").descending();
			}
			Pageable pageable = PageRequest.of(pageNum, size, sortable);
			
			
			

			if (search != null) {
				list = employeeService.listAllForSearch(pageable, search);
				System.out.println(list);
				
			} else {
				list = employeeService.ListAllPaging(pageable);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println(list);
		
		System.out.println(size);
		
		model.addAttribute("employee", new Employee());
		model.addAttribute("employees", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("totalItems", list.getTotalElements());
		model.addAttribute("pageSize", size);

		return "employeeManager";

	}
}