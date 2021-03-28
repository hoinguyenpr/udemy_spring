package com.dpm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.constant.Constants;
import com.dpm.dto.PressingMonitorDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.TypeProduct;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.PressingMornitorService;
import com.dpm.service.TypeProductService;

/**
 * 
 * @author LongVT7
 * 
 *
 */

@Controller
@RequestMapping(value = "/pressing-monitor")
public class PressingMornitorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PressingMornitorController.class);

	@Autowired
	private PressingMornitorService pressingMornitorService;

	@Autowired
	private MachineService machineService;

	@Autowired
	private TypeProductService typeProductService;

	@Autowired
	private IngredientBatchService ingredientBatchService;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = { "", "/" })
	public ModelAndView index(ModelMap model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "date", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date) {

		if (date == null) {
			date = LocalDateTime.now().toLocalDate();
		}

		Sort sortable = Sort.by("inputTime").descending();
		Page<PressingMornitor> list = null;
		
		try {

			Pageable pageable = PageRequest.of(page, size, sortable);

			list = pressingMornitorService.findAllByDay(date, pageable);

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}

		model.addAttribute("pressingMornitors", list);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", size);
		model.addAttribute("date", date);

		return new ModelAndView("pressing-monitor", model);
	}

	@PostMapping(value = "/create")
	public RedirectView createNewPressMonitor(
			@Valid @ModelAttribute("pressingMonitor") PressingMonitorDTO pressingMornitor,
			RedirectAttributes redirectAttr, BindingResult result) {

		if (result.hasFieldErrors()) {
			redirectAttr.addFlashAttribute("state", "CREATE_FAILED");
			return new RedirectView("/pressing-monitor");
		}

		pressingMornitor.setId(0);

		if (pressingMornitorService.insert(pressingMornitor)) {
			redirectAttr.addFlashAttribute("state", "CREATE_SUCCESS");
		} else {
			redirectAttr.addFlashAttribute("state", "CREATE_FAILED");
		}

		return new RedirectView("/pressing-monitor");
	}

	@GetMapping(value = "/update")
	@ResponseBody
	public PressingMonitorDTO updatePressMonitor(@RequestParam(value = "id") Integer id) {

		Optional<PressingMornitor> pm = pressingMornitorService.getById(id);
		if(pm.isPresent()){
			return new PressingMonitorDTO(pm.get());
		}
		return null;

	}

	@PostMapping(value = "/update")
	public RedirectView updatePressMonitor(@ModelAttribute("pressingMonitor") PressingMonitorDTO pressingMornitor,
			RedirectAttributes redirectAttributes) {

		if (pressingMornitorService.update(pressingMornitor)) {
			redirectAttributes.addFlashAttribute("state", "UPDATE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "UPDATE_FAILED");
		}

		return new RedirectView("/pressing-monitor");

	}

	@GetMapping(value = "/delete")
	public RedirectView deletePressMonitor(@RequestParam(value = "id") Integer id,
			RedirectAttributes redirectAttributes) {
		if (pressingMornitorService.delete(id)) {
			redirectAttributes.addFlashAttribute("state", "DELETE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "DELETE_FAILED");
		}

		return new RedirectView("/pressing-monitor");
	}

	@GetMapping(value = "/default-value")
	public RedirectView updateDefaultValue(@RequestParam(name = "id") Integer id,
			RedirectAttributes redirectAttributes) {

		if (pressingMornitorService.setDefaultValueById(id)) {
			redirectAttributes.addFlashAttribute("state", "UPDATE_DEFAULT_VALUE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "UPDATE_DEFAULT_VALUE_FAILED");
		}

		return new RedirectView("/pressing-monitor");
	}

	@PostMapping(value = "/default-value")
	public RedirectView updateDefaultValue(
			@ModelAttribute(value = "pressingMonitor") PressingMonitorDTO pressingMonitorDTO,
			RedirectAttributes redirectAttributes) {

		if (pressingMornitorService.setDefaultValueByEntity(pressingMonitorDTO.toEntity())) {
			redirectAttributes.addFlashAttribute("state", "UPDATE_DEFAULT_VALUE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "UPDATE_DEFAULT_VALUE_FAILED");
		}

		return new RedirectView("/pressing-monitor");
	}

	@PostMapping(value = "/approve")
	public RedirectView approve(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpServletRequest request,
			RedirectAttributes model) {

		if (pressingMornitorService.approve(ids)) {
			model.addFlashAttribute("state", "APPROVE_SUCCESS");
		} else {
			model.addFlashAttribute("state", "APPROVE_FAILED");
		}
		
		return new RedirectView(request.getHeader("referer"));
	}
	
	@PostMapping(value = "/refuse")
	public RedirectView refuse(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpServletRequest request,
			RedirectAttributes model) {
		
		if (pressingMornitorService.refuse(ids)) {
			model.addFlashAttribute("state", "REFUSE_SUCCESS");
		} else {
			model.addFlashAttribute("state", "REFUSE_FAILED");
		}
		
		return new RedirectView(request.getHeader("referer"));
	}
	
	@GetMapping("/rep-app")
	public ModelAndView reportApprovePage(ModelMap model,
			@RequestParam(value = "start", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate start,
			@RequestParam(value = "end", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate end,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "status", required = false, defaultValue = "-1") Integer status) {
		
		if (start == null) {
			start = LocalDateTime.now().toLocalDate();
		}

		if (end == null) {
			end = LocalDateTime.now().toLocalDate();
		}

		Sort sortable = Sort.by("inputTime").descending();
		Page<PressingMornitor> list = null;
		try {
			Pageable pageable = PageRequest.of(pageNum, size, sortable);
			if(status == -1) {
				list = pressingMornitorService.findByInputTimeBetween(start, end, pageable);
			} else {
				list = pressingMornitorService.findAllByStatusAndInputTimeBetween(status, start, end, pageable);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("page", pageNum);
		model.addAttribute("size", size);
		model.addAttribute("status", status);
		model.addAttribute("listPms", list);
		model.addAttribute("totalPages", list.getTotalPages());
		
		return new ModelAndView("pressing-monitor-app-rep", model);
	}

	@GetMapping(value = "/report/download")
	public ResponseEntity<?> exportExcel(HttpServletResponse response,
			@RequestParam(value = "start") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam(value = "end") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
			@RequestParam(value = "status") Integer status) {
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			File file = pressingMornitorService.exportExcelByDate(startDate, endDate, status);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set("Content-disposition", "attachment; filename=" + LocalDateTime.now().toString().replace("T", "_") +".xlsx");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            
            return ResponseEntity.ok().headers(headers).contentLength(file.length())
            		.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

		} catch (IOException e) {
			LOGGER.error("Backup Failure: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Backup Failure: " + e.getMessage());
		}
		
		return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ModelAttribute("pressingMonitor")
	public PressingMonitorDTO getDefaultValue(Authentication authentication) {
		Employee employee = employeeService.getEmployeeByUsername(authentication.getName());
		PressingMornitor monitor = pressingMornitorService.getDefaultValue();
		monitor.setCreateEmployee(employee);
		return new PressingMonitorDTO(monitor);
	}

	@ModelAttribute("listTypeProduct")
	public List<TypeProduct> getTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	@ModelAttribute("listMachines")
	public List<Machine> getListMachine() {
		return machineService.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING);
	}

	@ModelAttribute("listIngerdientBatch")
	public List<IngredientBatch> getListIngredientBatch() {
		return ingredientBatchService.fetchAll();
	}

}
