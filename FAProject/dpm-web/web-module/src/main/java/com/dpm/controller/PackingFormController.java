package com.dpm.controller;

import java.io.Console;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Sort;

import com.dpm.constant.Constants;
import com.dpm.dto.PackingFormDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.FinishedProduct;
import com.dpm.entity.Machine;
import com.dpm.entity.Packing;
import com.dpm.entity.PackingForm;
import com.dpm.entity.TypeOfPacking;
import com.dpm.entity.TypeProduct;
import com.dpm.service.EmployeeService;
import com.dpm.service.FinishedProductService;
import com.dpm.service.MachineService;
import com.dpm.service.PackingFormService;
import com.dpm.service.PackingService;
import com.dpm.service.TypeOfPackingService;
import com.dpm.service.TypeProductService;

/**
 * Author: HoiNX1 Modified date: 14/01/2020 11:37 AM Modifier:
 */

@Controller
@RequestMapping("/packing-form")
public class PackingFormController {

	// Autowired Service

	@Autowired
	TypeProductService typeProductService;

	@Autowired
	MachineService machineService;

	@Autowired
	FinishedProductService finishedProductService;

	@Autowired
	TypeOfPackingService typeOfPackingService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PackingFormService packingFormService;

	@Autowired
	PackingService packingService;

	// Binding data to form
	@GetMapping(path = "/list-packing-form")
	public String listPackingForm(Model model) {
	
		List<PackingForm> listPackingForms = packingFormService.getAllPackingForm();
		
		model.addAttribute("listPackingForms", listPackingForms);
		
		return "packing-form";
		
		

	}
	
	//Send data to view

	@GetMapping(path = "/report-packing-form")
	public String reportPackingForm() {
		return "packing-form-report";
	}

	@ModelAttribute("listMachine")
	public List<Machine> getAllMachine() {
		return machineService.findAllMachine();
	}

	@ModelAttribute("listEmployee")
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}

	@ModelAttribute("listTypeProduct")
	public List<TypeProduct> getAllTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	@ModelAttribute("listFinishedProduct")
	public List<FinishedProduct> FinishedProduct() {
		return finishedProductService.findAllFinishedProductByType(Constants.FINISHED_PRODUCT);
	}

	@ModelAttribute("listSemiProduct")
	public List<FinishedProduct> SemiProduct() {
		return finishedProductService.findAllFinishedProductByType(Constants.SEMI_PRODUCT);
	}

	@ModelAttribute("listPacking")
	public List<Packing> getAllPacking() {
		return packingService.listAll();
	}

	@ModelAttribute("listPackingType")
	public List<TypeOfPacking> listAllTypeOfPacking() {
		return typeOfPackingService.getAllTypePacking();
	}

	@PostMapping(path = "/save")
	public String saveOrUpdate(@ModelAttribute("packingFormDTO") PackingFormDTO packingFormDTO) {
		// get dto
		System.out.println("ashdgshagdshad hahahahahah");

		// mapping
//			PackingForm p = packingFormDTO.mapping();
//			//save
//			
//			packingFormService.save(packingForm);
//		System.out.println("TestDTO............");
		System.out.println("TestDTO " + packingFormDTO.toString());
		
	
		try {
			packingFormService.saveOrUpdate(packingFormDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list-packing-form";
	}
	
	
	/*
	 * 
	 */
	@GetMapping(value = "/getById/{id}")
	@ResponseBody
	public PackingFormDTO getPackingFormById(@PathVariable("id") Integer id) {
		
		PackingForm packingForm = packingFormService.getPackingFormById(id);
		
		PackingFormDTO packingFormDTO = new PackingFormDTO();
		packingFormDTO = setAttributePackingFormDTO(packingForm, packingFormDTO);

		return packingFormDTO;
	}
	
	
	private PackingFormDTO setAttributePackingFormDTO(PackingForm packingForm, PackingFormDTO packingFormDTO) {
		
		packingFormDTO.setId(packingForm.getId());
		packingFormDTO.setTypeProductID(packingForm.getTypeProduct().getId());
		packingFormDTO.setMachineID(packingForm.getMachine().getId());
		packingFormDTO.setCreateManID(packingForm.getCreateman().getId());
		packingFormDTO.setPackingDate(packingForm.getPackingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		packingFormDTO.setSemi_Product_Lot(packingForm.getSemiProduct().getLotCode().getLotCode());
		packingFormDTO.setTimePacking(packingForm.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		packingFormDTO.setFinished_Product_Lot(packingForm.getFinishedProduct().getLotCode().getLotCode());
		packingFormDTO.setQuanity(packingForm.getQuantity());
		packingFormDTO.setShift(packingForm.getShift());
		packingFormDTO.setPackingID(packingForm.getPacking().getId());
		packingFormDTO.setForeManID(packingForm.getForeman().getId());
		packingFormDTO.setTypeOfPackingID(packingForm.getTypeofpacking().getId());
		packingFormDTO.setInChargeManID(packingForm.getPersonInCharge().getId());
		packingFormDTO.setPackingQuanity(packingForm.getPackingQuanity());
		packingFormDTO.setSttNo(packingForm.getSttNo());
		packingFormDTO.setStatus(packingForm.getStatus());
			
		return packingFormDTO;
	}
	
	@GetMapping(value = "/deleteById/{id}")
	@ResponseBody
	public String deletePackingFormById(@PathVariable("id") Integer packingFormId) {
		try {
			if(packingFormService.deletePackingFormByID(packingFormId)) {
				return Constants.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.FAILED;
	}
}
 