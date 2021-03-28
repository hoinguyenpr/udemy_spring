package com.dpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpm.entity.Machine;
import com.dpm.service.MachineService;

@Controller
@RequestMapping("/form-machine")
public class FormMachineController {
	
	@Autowired
	public MachineService machineService;
	
	@GetMapping("/listMachine")
	public String showAll(Model model) {
		List<Machine> machines = machineService.findAllMachine();
	    model.addAttribute("machines", machines);
	    model.addAttribute("machine", new Machine());
	    return "form-machine";
	}
	
	@RequestMapping (value = "/delete", method = RequestMethod.POST)
	public String postDeleteAdditive (@RequestParam("deleteId") String id) {
		Integer deleteId = Integer.parseInt(id);
		
		// Delete additive by id
		machineService.deleteMachineById(deleteId);
		
		// Return view
		return "redirect:/listMachine";
	}
	
	// -------------------------------------------------
	@RequestMapping (value = "/save", method = RequestMethod.POST)
	public String postEditOrCreateAdditive (@ModelAttribute ("machine") Machine machine) {
		
		System.out.println(machine.getMachineImportDate());
		
		// Edit or create new additive
		machineService.editOrCreateMachine(machine);
		
		// Return view
		return "redirect:/listMachine";
		
	}
	
//	@GetMapping(value = { "/", "search", "maloaisanpham" })
//	public String getAllMaLoaiSanPham(Model model,
//			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
//			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
//			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
//			@RequestParam(name = "search", required = false) String search) {
//		
//		Sort sortable = null;
//		Page<Machine> list = null;
//		try {
//			if (sort.equals("ASC")) {
//				sortable = Sort.by("id").ascending();
//			}
//			if (sort.equals("DESC")) {
//				sortable = Sort.by("id").descending();
//			}
//			Pageable pageable = PageRequest.of(pageNum, size, sortable);
//
//			if (search != null) {
//				list = machineService.listAllForSearch(pageable, search);
//			} else {
//				list = machineService.getListSupplierCodePaging(pageable);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		model.addAttribute("suppliers", list);
//		model.addAttribute("search", search);
//		model.addAttribute("currentPage", pageNum);
//		model.addAttribute("totalPages", list.getTotalPages());
//		model.addAttribute("totalItems", list.getTotalElements());
//		model.addAttribute("pageSize", size);
//		model.addAttribute("supplier", new Machine());
//
//		return "listMachine";
//
//	}



	
}
