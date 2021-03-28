package com.dpm.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dpm.dto.BrixpHTrackingDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.Packing;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TrackingPHBx;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.PackingService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TrackingPHBxFormService;
import com.dpm.service.TypeProductService;

@Controller
@RequestMapping(value = "/Brix-pH-monitoring")
public class TrackingpHBrixController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingpHBrixController.class);
	private static List<TrackingPHBx> trackingForms = new ArrayList<TrackingPHBx>();
	private static Page<TrackingPHBx> showTrackingForms;
	public String filterDate;

	@Autowired
	DefaultSettingService defaultService;

	@Autowired
	MachineService machineService;

	@Autowired
	ProductLotSevice productLotService;

	@Autowired
	TypeProductService typeProductService;

	@Autowired
	TrackingPHBxFormService trackingPHBx;

	@Autowired
	PackingService packingService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	IngredientBatchService ingredientBatchService;

	@ModelAttribute("products")
	public List<ProductLot> listAllProduct() {
		return productLotService.getAllProductLot();
	}

	@ModelAttribute("machines")
	public List<Machine> listAllMachine() {
		return machineService.findAllMachine();
	}

	@ModelAttribute("listEmployee")
	public List<Employee> listAllEmployee() {
		return employeeService.getAllEmployee();
	}

	@ModelAttribute("ingredients")
	public List<IngredientBatch> listAllIngredientBatch() {
		return ingredientBatchService.fetchAll();
	}

	@ModelAttribute("packings")
	public List<Packing> listAllPacking() {
		return packingService.listAll();
	}

	@ModelAttribute("emptyForm")
	public BrixpHTrackingDTO DTO() {
		return new BrixpHTrackingDTO(defaultService.getDefaultTrackingForm());
	}

	@GetMapping(value = "/")
	public String getAll(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "filterDate", required = false) String date,
			@RequestParam(name = "status", required = false, defaultValue = "-1") Integer status) {

		if (status == null || date == null) {
			model.addAttribute("status", status);
			return "pH-brix-monitoring-form";
		}

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate filterDate = LocalDate.parse(date, dateFormatter);
		trackingForms = trackingPHBx.listFormsByDate(filterDate);

		if (status == -1) {
			
			showTrackingForms = trackingPHBx.listFormsByDate(filterDate, page);
		} else {
			
			showTrackingForms = trackingPHBx.listFormsByStatus(filterDate, status, page);
		}
		model.addAttribute("status", status);
		model.addAttribute("totalPages", showTrackingForms.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("filterDate", date);
		model.addAttribute("pagingForms", showTrackingForms);
		model.addAttribute("allTrackingForms", trackingForms);
		return "pH-brix-monitoring-form";
	}

	@GetMapping(value = "/add")
	public String add(Model model,
			@RequestParam(name = "filterDate", required = false) String date,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate filterDate = LocalDate.parse(date, dateFormatter);
		showTrackingForms = trackingPHBx.listFormsByDate(filterDate, page);
		trackingForms = trackingPHBx.listFormsByDate(filterDate);
		model.addAttribute("totalPages", showTrackingForms.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("filterDate", filterDate);
		model.addAttribute("trackingForms", trackingForms);
		model.addAttribute("pagingForms", showTrackingForms);
		return "pH-brix-add-form";
	}

	@GetMapping(value = "/save")
	public String save(
			@ModelAttribute("emptyForm") @Valid BrixpHTrackingDTO trackingFormDTO,
			BindingResult bindingResult, RedirectAttributes ra, Model model,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("trackingForms", trackingForms);
			model.addAttribute("filterDate", trackingFormDTO.getDateCreate());
			return "pH-brix-add-form";
		}

		TrackingPHBx trackingForm = new TrackingPHBx(trackingFormDTO);
		trackingPHBx.addForm(trackingForm);
		return "redirect:" + request.getHeader("referer");
	}

	@GetMapping(value = "/save/default")
	public String saveDefault(
			@ModelAttribute("emptyForm") BrixpHTrackingDTO trackingFormDTO,
			HttpServletRequest request) {
		defaultService.addDefaultTrackingForm(trackingFormDTO);
		return "redirect:" + request.getHeader("referer");
	}

	@GetMapping(value = "/default")
	public String saveChoosenDefault(@RequestParam("id") Integer id,
			HttpServletRequest request) {
		BrixpHTrackingDTO trackingFormDTO = new BrixpHTrackingDTO(
				trackingPHBx.findFormById(id));
		defaultService.addDefaultTrackingForm(trackingFormDTO);
		return "redirect:" + request.getHeader("referer");
	}

	@GetMapping(value = "/delete")
	public String deletePacking(@RequestParam("id") Integer id,
			HttpServletRequest request) {
		trackingPHBx.deleteById(id);
		return "redirect:" + request.getHeader("referer");
	}

	@GetMapping(value = "/report")
	public void exportExcel(HttpServletResponse response) {
		try {
			response.setContentType("application/octet-stream");
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=pHBrix_monitor_report.xlsx";
			response.setHeader(headerKey, headerValue);
			trackingPHBx.exportExcel(response, trackingForms);
		} catch (Exception e) {
			LOGGER.error("download");
		}
	}

	@GetMapping(value = "/approval")
	public String approval(@RequestParam("id") Integer id,
			@RequestParam("st") Integer approvalStatus,
			@RequestParam("status") Integer status, HttpServletRequest request) {
		TrackingPHBx approvalForm = trackingPHBx.findFormById(id);
		approvalForm.setStatus(approvalStatus);
		trackingPHBx.addForm(approvalForm);
		return "redirect:" + request.getHeader("referer");
	}
	
	@GetMapping(value="/approval-selected")
	public String approvalSelected(@RequestParam("ids")Integer[] listId, 
			@RequestParam("selectedStatus") Integer status,
			HttpServletRequest request) {
		
		
		trackingPHBx.approvalSelected(listId, status);
		
		return "redirect:" + request.getHeader("referer");
	}
	
	// TODO ResponeBody
	@PostMapping(value = "/formBy")
	@ResponseBody
	public TrackingPHBx getFormByBatch(@RequestBody Integer batchNo) {
		if (batchNo > trackingForms.size()) {
			return defaultService.getDefaultTrackingForm();
		} else {
			TrackingPHBx form = trackingForms.get(batchNo - 1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime time = LocalTime.parse(form.getPumpingTime().format(formatter));
			form.setPumpingTime(time);
			return form;

		}

	}

	@PostMapping(value = "/typeProductBy")
	@ResponseBody
	public TypeProduct getTypeProduct(@RequestBody Integer id) {

		return productLotService.findById(id).getTypeProduct();
	}

}
