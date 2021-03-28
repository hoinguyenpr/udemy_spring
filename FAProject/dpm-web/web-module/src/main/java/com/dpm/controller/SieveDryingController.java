package com.dpm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.dao.SieveDryingCustomDAO;
import com.dpm.dao.SieveDryingDAO;
import com.dpm.dto.SieveDryingDTO;
import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.ProductLot;
import com.dpm.entity.SieveDrying;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.SieveDryingService;
import com.dpm.service.TypeProductService;
import com.dpm.utility.EmployeePosition;
import com.dpm.utility.Status;

/**
 * 
 * 
 * @Author TruongDD
 * 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/sieve-drying")
public class SieveDryingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SieveDryingController.class);
	
	@Autowired
	SieveDryingCustomDAO sdCustomDao;
	
	@Autowired
	private IngredientBatchService ingredientBatchService;
	
	@Autowired
	private SieveDryingDAO sieveDryingDAO;
	
	@Autowired
	private TypeProductService typeProductService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProductLotSevice lotSevice;
	
	@Autowired
	private IngredientBatchService matService;
	
	@Autowired
	private SieveDryingService sieveDryingService;
	
	@Autowired
	private MachineService machineService;
	
	@Autowired
	DefaultSettingService defSettingService;
	
	@ModelAttribute("listTypeProduct")
	public List<TypeProduct> getTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	@ModelAttribute("listMachines")
	public List<Machine> getListMachine() {
		return machineService.findAllMachine();
	}

	@ModelAttribute("listIngerdientBatch")
	public List<IngredientBatch> getListIngredientBatch() {
		return ingredientBatchService.fetchAll();
	}
	
	@ModelAttribute("listQC")
	public List<Employee> getAllQCs(){
		return employeeService.findByPosition(EmployeePosition.Qc.toString());
	}
	
	@ModelAttribute("listLots")
	public List<ProductLot> getPrdLots(){
		return lotSevice.getAllProductLot();
	}
	
	@ModelAttribute("listWorkers")
	public List<Employee> getAllWorkers(){
		return employeeService.findByPosition(EmployeePosition.Worker.toString());
	}
	
	@ModelAttribute("listVerifiers")
	public List<Employee> getAllVerifiers(){
		return employeeService.findByPosition(EmployeePosition.Verifier.toString());
	}

	@GetMapping(path = "/sieve-drying-input")
	public ModelAndView newRecord(ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
		
		

		List<Employee> QCs = employeeService.findByPosition(EmployeePosition.Qc.toString());
		
		List<Employee> workers = employeeService.findByPosition(EmployeePosition.Worker.toString());
		
		List<Employee> verifiers = employeeService.findByPosition(EmployeePosition.Verifier.toString());

		//Update by DinhDN 15-01-2021 02:03:00
		List<ProductLot> productLots = lotSevice.getAllProductLot();
		
		List<IngredientBatch> igrBatch = matService.getAll();
		
		List<TypeProduct> productList = typeProductService.getAllTypeProduct();
		
		SieveDrying defaultSieveDrying = defSettingService.getDefaultSieveDrying();
		
		
		List<Machine> machineList = machineService.findAllMachine();
		
		Sort sortable = Sort.by("id").descending();
		Page<SieveDrying> sieveDryingList = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);
			
			sieveDryingList = sieveDryingDAO.getAllRecordsByDate(pageable, LocalDateTime.now().toLocalDate());

			//sieveDryingList = sieveDryingDAO.getAllAvailable(pageable);

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		model.addAttribute("verifiers", verifiers);
		model.addAttribute("workers", workers);
		model.addAttribute("QCs", QCs);
		model.addAttribute("lots", productLots);
		model.addAttribute("igrBatch", igrBatch);
		model.addAttribute("prdType", productList);
		model.addAttribute("machines", machineList);
		model.addAttribute("pageId", pageId);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", sieveDryingList.getTotalPages());
		//Get all old data
		model.addAttribute("listSieveDrying", sieveDryingList);
		model.addAttribute("defaultSieveDrying",defaultSieveDrying);
		
		return new ModelAndView("sieve-drying-input", model);
	}
	
	@ModelAttribute("listSieveDrying")
	public List<SieveDrying> getAllButNotDefault(){
		return sieveDryingService.getAllButNotDefault();
	}
	
	@ModelAttribute("listSieveDryingPending")
	public List<SieveDrying> getPending(){
		return sieveDryingService.getPending();
	}
	
	@GetMapping(path = "/sieve-drying-appr")
	public ModelAndView approveRecord(ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "status", required = false, defaultValue = "0") Integer status) {
		Sort sortable = Sort.by("id").descending();
		Page<SieveDrying> list = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);
			switch (status.intValue()) {
			case 0:
				list = sieveDryingDAO.getPending(pageable);
				break;
			case 1:
				list = sieveDryingDAO.getApproved(pageable);
				break;
			default:
				list = sieveDryingDAO.getApprovedAndPending(pageable);
				break;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("status", status);
		return new ModelAndView ("sieve-drying-appr", model);
	}
	
	@GetMapping(path = "/sieve-drying-report")
	public ModelAndView reportRecord(ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
		
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> sieveDryingList = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			sieveDryingList = sieveDryingDAO.getAllButNotDefaultForReport(pageable);

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		SieveDryingReportDTO defaultReportDTO = new SieveDryingReportDTO();
						
		defaultReportDTO.setIngredientBatchCode(0);
		defaultReportDTO.setLotCode(0);
		defaultReportDTO.setMachineId(0);
		defaultReportDTO.setTypeProductId(0);
		defaultReportDTO.setStatus(0);

		model.addAttribute("listSieveDrying", sieveDryingList);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", sieveDryingList.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastReportDTO", defaultReportDTO);
		
		return new ModelAndView("sieve-drying-report", model);
	}
	
	@GetMapping(value = "/getRecordBy")
	@ResponseBody 
	public SieveDrying getById(@RequestParam("id") int id) {
		System.out.println("Catched with ID: " + id);
		return sieveDryingDAO.findById(id).get();
	}
	
	@PostMapping(value = "/get-report")
	public ModelAndView getReport(@ModelAttribute SieveDryingReportDTO rdto, ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
		
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> list = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			list = sdCustomDao.getReport(rdto, pageable);
			
			list.getContent().forEach(System.out::println);
			model.addAttribute("listSieveDrying", list);
			model.addAttribute("pageId", pageId);
			model.addAttribute("sieveDrying", new SieveDryingDTO());
			model.addAttribute("totalPages", list.getTotalPages());
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("lastReportDTO",rdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("sieve-drying-report", model);
	}
	
	@PostMapping(value = "/create")
	public RedirectView create(@ModelAttribute SieveDryingDTO dto, RedirectAttributes redirectAttr) {
		SieveDrying sieveDrying = new SieveDrying();
		String results = null;
		if (sieveDryingService.isValid(dto)) {
			Optional<Employee> qc = employeeService.get(dto.getQCId());
			Optional<Employee> verifier = employeeService.get(dto.getVerifierId());
			Optional<Employee> worker = employeeService.get(dto.getWorkerId());
			Optional<ProductLot> lotCode = lotSevice.get(dto.getLotId());
			Optional<TypeProduct> typeProduct = typeProductService.get(dto.getTypeProductId());
			Optional<IngredientBatch> igrBatch = ingredientBatchService.get(dto.getIngredientBatchCode());
			Optional<Machine> machine = machineService.get(dto.getMachineId());
	
			sieveDrying = SieveDrying.mapping(dto);
			
			sieveDrying.setQc(qc.get());
			sieveDrying.setWorker(worker.get());
			sieveDrying.setVerifier(verifier.get());
			sieveDrying.setLotId(lotCode.get());
			sieveDrying.setTypeProduct(typeProduct.get());
			sieveDrying.setIngredientBatch(igrBatch.get());
			sieveDrying.setMachine(machine.get());
			sieveDrying.setStatus(Status.Pending.toString());
			
			results = sieveDryingService.createStr(sieveDrying);
			
		}else{results = "CREATE_FAILED";}
		redirectAttr.addFlashAttribute("state", results);
		return new RedirectView("/sieve-drying/sieve-drying-input");
	}
	
	@PostMapping(value = "/update")
	public RedirectView update(@ModelAttribute SieveDryingDTO dto,RedirectAttributes redirectAttr) {
		String results = null;
		if (sieveDryingService.isValid(dto)) {
			results = sieveDryingService.updateSieveDrying(dto);
		}else {
			results = "UPDATE_FAILED";
		}
		
		redirectAttr.addFlashAttribute("state", results);
		return new RedirectView("/sieve-drying/sieve-drying-input");
	}
	
	@PostMapping(value = "/delete")
	public RedirectView delete(@RequestParam int id, RedirectAttributes redirectAttr) {
		String results = sieveDryingService.deleteSieveDrying(id);
		redirectAttr.addFlashAttribute("state", results);
		return new RedirectView("/sieve-drying/sieve-drying-input");
	}
	
	@PostMapping(value = "/approve")
	public ModelAndView approve(@ModelAttribute SieveDryingReportDTO rdto, ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "id", required = true) Integer id) {
		
		LOGGER.info("Function: /approve");
		String approveResult = sieveDryingService.approveSieveDrying(id);
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			list = sdCustomDao.getReport(rdto, pageable);
			
		} catch (Exception e) {
			LOGGER.error("Error: " + e.getMessage());
		}
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastReportDTO",rdto);
		return new ModelAndView("sieve-drying-report", model);
	}
	
	@PostMapping(value = "/approve-selected")
	public ModelAndView approveSelected(@ModelAttribute SieveDryingReportDTO rdto, ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			
			@RequestParam(name = "idList", required = true) List<Integer> idList) {
		LOGGER.info(idList.toString());
		
		String approveResult = sieveDryingService.approveSieveDrying(idList);
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
		try {
			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);
			list = sdCustomDao.getReport(rdto, pageable);
			if (list.isEmpty()) {
				pageId--;
				pageable = PageRequest.of(pageId, pageSize, sortable);
				list = sdCustomDao.getReport(rdto, pageable);
			}
		} catch (Exception e) {
			LOGGER.error("Error: " + e.getMessage());
		}
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastReportDTO",rdto);
		return new ModelAndView("sieve-drying-report", model);
	}
	
	@PostMapping(value = "/reject-selected")
	public ModelAndView rejectSelected(@ModelAttribute SieveDryingReportDTO rdto, ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "idList", required = true) List<Integer> idList) {
		LOGGER.info(idList.toString());
		String approveResult = sieveDryingService.rejectSieveDrying(idList);
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
		try {
			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);
			list = sdCustomDao.getReport(rdto, pageable);
		} catch (Exception e) {
			LOGGER.error("Error: " + e.getMessage());
		}
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastReportDTO",rdto);
		return new ModelAndView("sieve-drying-report", model);
	}

	@PostMapping(value = "/reject")
	public ModelAndView reject(@ModelAttribute SieveDryingReportDTO rdto, ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "id", required = true) Integer id) {
		
		LOGGER.info("Function: /reject");
		String rejectResult = sieveDryingService.rejectSieveDrying(id);
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			list = sdCustomDao.getReport(rdto, pageable);
			
		} catch (Exception e) {
			LOGGER.error("Error: " + e.getMessage());
		}
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastReportDTO",rdto);
		return new ModelAndView("sieve-drying-report", model);
	}
	
	@PostMapping(value = "/set-default")
	public RedirectView config(@ModelAttribute SieveDryingDTO dto, RedirectAttributes redirectAttr) {
		LOGGER.info("Catched default with ID: " + dto.getId());
		String results;
		if (dto.getId()!=0) {
			results = defSettingService.setDefaultSieveDrying(sieveDryingService.getSieveDryingById(dto.getId()));
		}
		else {
			results = sieveDryingService.setDefault(dto);
		}
		redirectAttr.addFlashAttribute("state", results);
		return new RedirectView("/sieve-drying/sieve-drying-input");

	}
	
	@PostMapping(value="/get-list")
	public ModelAndView getList(ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
		
		List<Employee> QCs = employeeService.findByPosition(EmployeePosition.Qc.toString());
		
		List<Employee> workers = employeeService.findByPosition(EmployeePosition.Worker.toString());
		
		List<Employee> verifiers = employeeService.findByPosition(EmployeePosition.Verifier.toString());

		//Update by DinhDN 15-01-2021 02:03:00
		List<ProductLot> productLots = lotSevice.getAllProductLot();
		
		List<IngredientBatch> igrBatch = ingredientBatchService.getAll();
		
		List<TypeProduct> productList = typeProductService.getAllTypeProduct();
		
		SieveDrying defaultSieveDrying = defSettingService.getDefaultSieveDrying();
		
		List<Machine> machineList = machineService.findAllMachine();
	
		Sort sortable = Sort.by("input_date").descending();
		Page<SieveDrying> sieveDryingList = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			sieveDryingList = sieveDryingDAO.getAllRecordsByDate(pageable, LocalDateTime.now().toLocalDate());
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		model.addAttribute("verifiers", verifiers);
		model.addAttribute("workers", workers);
		model.addAttribute("QCs", QCs);
		model.addAttribute("lots", productLots);
		model.addAttribute("igrBatch", igrBatch);
		model.addAttribute("prdType", productList);
		model.addAttribute("machines", machineList);
		model.addAttribute("pageId", pageId);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", sieveDryingList.getTotalPages());
		//Get all old data
		model.addAttribute("listSieveDrying", sieveDryingList);
		model.addAttribute("defaultSieveDrying",defaultSieveDrying);
				
		return new ModelAndView("sieve-drying-input", model);
	}
	
	@PostMapping(value="/get-appr-list")
	public ModelAndView getApprList(ModelMap model,
			@RequestParam(name = "pageId", required = false, defaultValue = "0") Integer pageId,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "status", required = false, defaultValue = "0") Integer status) {
		
		Sort sortable = Sort.by("id").descending();
		Page<SieveDrying> list = null;
		try {

			Pageable pageable = PageRequest.of(pageId, pageSize, sortable);

			System.out.println("********SIEVE DRYING REPORT STATUS CODE: " + status.toString());
			switch (status.intValue()) {
			case 0:
				list = sieveDryingDAO.getPending(pageable);
				break;
			case 1:
				list = sieveDryingDAO.getApproved(pageable);
				break;
			default:
				list = sieveDryingDAO.getApprovedAndPending(pageable);
				break;
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		model.addAttribute("listSieveDrying", list);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sieveDrying", new SieveDryingDTO());
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("status", status);
		
		return new ModelAndView("sieve-drying-appr", model);
	}
	
	@PostMapping(value = "/get-report/download")
	public void exportExcel(HttpServletResponse response, @ModelAttribute SieveDryingReportDTO rdto) {
		System.out.println("------------------------------------------------------------");
		System.out.println("Get Report");
		try {
			response.setContentType("application/octet-stream");
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=report.xlsx";
	        response.setHeader(headerKey, headerValue);
	        
	        sieveDryingService.exportReport(response, rdto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
