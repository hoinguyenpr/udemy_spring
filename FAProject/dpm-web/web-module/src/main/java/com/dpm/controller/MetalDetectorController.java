/**
 * 
 */
package com.dpm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpm.constant.Constants;
import com.dpm.dto.MetalDetectorDTO;
import com.dpm.dto.MetalDetectorFilterDTO;
import com.dpm.entity.Department;
import com.dpm.entity.Employee;
import com.dpm.entity.MetalDetector;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.DepartmentService;
import com.dpm.service.EmployeeService;
import com.dpm.service.MetalDetectorService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeProductService;

/**
 * @author DinhDN
 *
 */

@Controller
@RequestMapping(value = "/metal-detector")
public class MetalDetectorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetalDetectorController.class);

	@Autowired
	private MetalDetectorService metalDetectorService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TypeProductService typeProductService;

	@Autowired
	private ProductLotSevice productLotSevice;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DefaultSettingService defaultSettingService;

	/**
	 * Function delete all space character of field when get data from client input.
	 * 
	 * @param dataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	/**
	 * Function view home page.
	 * 
	 * @return page metal-detector-report.html
	 */
	@GetMapping(value = { "/" })
	public String home() {
		return "metal-detector";
	}

	@PostMapping(value = { "/getListHomeFormMetalDetector" })
	@ResponseBody
	public Page<MetalDetector> getListHomeFormMetalDetector(
			@RequestParam(name = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = Constants.DEFAULT_LIST_SIZE) Integer size,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "startDate", required = false) String stringStartDate,
			@RequestParam(name = "endDate", required = false) String stringEndDate) {

		LOGGER.info("Function getListHomeFormMetalDetector of class MetalDetectorController.");

		// Set paging configure
		Pageable pageable = PageRequest.of(pageNumber, size);

		// Create new list to store data get from database.
		Page<MetalDetector> pageMetalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());

		if (!stringStartDate.isEmpty() && !stringEndDate.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date startDate = dateFormat.parse(stringStartDate.replace("/", "-"));
				Date endDate = dateFormat.parse(stringEndDate.replace("/", "-"));
				if (!stringStartDate.isEmpty() && !stringEndDate.isEmpty() && search == null) {
					pageMetalDetectors = metalDetectorService.searchMetalDetector(startDate, endDate, pageable);
				} else if (!stringStartDate.isEmpty() && !stringEndDate.isEmpty() && search != null) {
					pageMetalDetectors = metalDetectorService.searchMetalDetector(startDate, endDate, search, pageable);
				} else {
					pageMetalDetectors = metalDetectorService.getAllMetalDetector(pageable);
				}

			} catch (ParseException e) {
				LOGGER.error("Error convert date: " + e.getMessage());
			} catch (Exception e) {
				LOGGER.error("ERROR: " + e.getMessage());
			}
		}

		return pageMetalDetectors;
	}

	/**
	 * Function view page report.
	 * 
	 * @return page metal-detector-report.html
	 */
	@GetMapping(value = { "/report" })
	public String report() {
		return "metal-detector-report";
	}

	/**
	 * Function view page approve.
	 * 
	 * @return page metal-detector-report.html
	 */
	@GetMapping(value = { "/approve" })
	public String approve() {
		return "metal-detector-approve";
	}

	/**
	 * Function update status of metalDetector.
	 * 
	 * @param data
	 * @param status
	 * @return String. if success String will be empty, otherwise return id error.
	 */
	@PostMapping(value = { "/updateStatusMetalDetector" })
	@ResponseBody
	public String updateStatusMetalDetector(@RequestParam(name = "data") String data,
			@RequestParam(name = "status") String status) {
		LOGGER.info("Function updateStatusMetalDetector of class MetalDetectorController. ");

		String message = "";
		String arrayId[] = data.split(",");
		for (int i = 0; i < arrayId.length; i++) {
			try {
				MetalDetector metalDetector = metalDetectorService.getMetalDetectorById(Integer.valueOf(arrayId[i]));
				if (status.equalsIgnoreCase(Constants.STATUS_APPROVED)) {
					metalDetector.setStatus(Constants.STATUS_APPROVED);
					metalDetectorService.saveOrUpdate(metalDetector);
				} else if (status.equalsIgnoreCase(Constants.STATUS_REJECTED)) {
					metalDetector.setStatus(Constants.STATUS_REJECTED);
					metalDetectorService.saveOrUpdate(metalDetector);
				}
			} catch (NumberFormatException e) {
				LOGGER.error("ERROR convert id from String to Integer: " + e.getMessage());
				message += arrayId[i];
			}
		}
		return message;
	}

	@PostMapping(value = { "/getListApproveFormMetalDetector" })
	@ResponseBody
	public Page<MetalDetector> getListApproveFormMetalDetector(
			@RequestParam(name = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = Constants.DEFAULT_LIST_SIZE) Integer size) {
		LOGGER.info("Function getListApproveFormMetalDetector of class MetalDetectorController.");

		// Set paging configure
		Pageable pageable = PageRequest.of(pageNumber, size);

		// Create new list to store data get from database.
		Page<MetalDetector> pageMetalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());

		try {
			pageMetalDetectors = metalDetectorService.getAllMetalDetectorSortByStatus(pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR getListApproveFormMetalDetector : " + e.getMessage());
		}

		return pageMetalDetectors;
	}

	/**
	 * Function get list metal detector.
	 * 
	 * @return Json. List Object MetalDetector.
	 */
	@PostMapping(value = { "/getListPeportFormMetalDetector" })
	@ResponseBody
	public Page<MetalDetector> getListPeportFormMetalDetector(
			@ModelAttribute("metalDetectorFilterDTO") MetalDetectorFilterDTO metalDetectorFilterDTO,
			@RequestParam(name = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "size", required = false, defaultValue = Constants.DEFAULT_LIST_SIZE) Integer size) {
		LOGGER.info("Function getListPeportFormMetalDetector of class MetalDetectorController.");

		// Set paging configure
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("id").descending());

		// Create new list to store data get from database.
		Page<MetalDetector> pageMetalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());

		try {
			pageMetalDetectors = metalDetectorService.filterAllByParameter(metalDetectorFilterDTO, pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR get data record filter: " + e.getMessage());
		}

		return pageMetalDetectors;
	}

	@PostMapping(path = "/filterValidate")
	@ResponseBody
	public List<ObjectError> filterValidate(
			@Valid @ModelAttribute("metalDetectorFilterDTO") MetalDetectorFilterDTO metalDetectorFilterDTO,
			BindingResult bindingResult, @ModelAttribute("departments") List<Object> departments,
			@ModelAttribute("typeProducts") List<Object> typeProducts,
			@ModelAttribute("productLots") List<Object> productLots,
			@ModelAttribute("employees") List<Object> employees, Model model,
			@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam(name = "size", required = false) Integer size) {
		LOGGER.info("Function filterValidate of class MetalDetectorController.");
		List<ObjectError> objectErrors = new ArrayList<ObjectError>();

		if (bindingResult.hasErrors()) {
			objectErrors = bindingResult.getAllErrors().stream().collect(Collectors.toList());
		} else {
			// Validate department
			objectErrors = metalDetectorService.validateOject(objectErrors, departments,
					metalDetectorFilterDTO.getDepartmentCode(), "departmentCode");
			// Validate typeProduct
			objectErrors = metalDetectorService.validateOject(objectErrors, typeProducts,
					metalDetectorFilterDTO.getTypeProductCode(), "typeProductCode");
			// Validate productLot
			objectErrors = metalDetectorService.validateOject(objectErrors, productLots,
					metalDetectorFilterDTO.getLotNo(), "lotNo");
			// Validate employee
			objectErrors = metalDetectorService.validateOject(objectErrors, employees,
					metalDetectorFilterDTO.getUserName(), "userName");
			// Validate DateTime
			objectErrors = metalDetectorService.validateDateTime(objectErrors, metalDetectorFilterDTO.getStartDate(),
					"startDate");
			objectErrors = metalDetectorService.validateDateTime(objectErrors, metalDetectorFilterDTO.getEndDate(),
					"endDate");
		}
		return objectErrors;
	}

	@PostMapping(value = "/exportExcel")
	public void exportExcel(HttpServletResponse response,
			@Valid @ModelAttribute("metalDetectorFilterDTO") MetalDetectorFilterDTO metalDetectorFilterDTO,
			BindingResult bindingResult, @ModelAttribute("departments") List<Object> departments,
			@ModelAttribute("typeProducts") List<Object> typeProducts,
			@ModelAttribute("productLots") List<Object> productLots,
			@ModelAttribute("employees") List<Object> employees) {

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Metal detector report.xlsx";
		response.setHeader(headerKey, headerValue);
		try {
			metalDetectorService.exportExcel(response, metalDetectorFilterDTO);
		} catch (Exception e) {
			LOGGER.info("Error export to excell: " + e.getMessage());
		}
	}

	/**
	 * Function validate form metalDetector and save or update object to database
	 * 
	 * @param metalDetectorDTO
	 * @param bindingResult
	 * @return Object ISON
	 */
	@PostMapping(path = "/save")
	@ResponseBody
	public List<ObjectError> saveOrUpdate(@Valid @ModelAttribute("metalDetectorDTO") MetalDetectorDTO metalDetectorDTO,
			BindingResult bindingResult) {
		LOGGER.info("Function saveOrUpdate of class MetalDetectorController.");
		List<ObjectError> objectErrors = new ArrayList<ObjectError>();

		if (bindingResult.hasErrors()) {
			objectErrors = bindingResult.getAllErrors().stream().collect(Collectors.toList());
		} else {
			try {
				metalDetectorService.saveOrUpdate(metalDetectorDTO);
			} catch (Exception e) {
				ObjectError error = new ObjectError("errorSave", "Can't save");
				objectErrors.add(error);
				LOGGER.error("ERROR while save new MetalDetector: " + e.getMessage());
			}
		}

		return objectErrors;

	}

	/**
	 * Function validate data user input form and create or update data to table
	 * default_setting
	 * 
	 * @param metalDetectorDTO
	 * @param bindingResult
	 * @return Object Json
	 */
	@PostMapping(path = "/saveOrUpdateDefaultSetting")
	@ResponseBody
	public List<ObjectError> saveOrUpdateDefaultSetting(
			@Valid @ModelAttribute("metalDetectorDTO") MetalDetectorDTO metalDetectorDTO, BindingResult bindingResult) {
		LOGGER.info("Function saveOrUpdateDefaultSetting of class MetalDetectorController.");
		List<ObjectError> objectErrors = new ArrayList<ObjectError>();

		if (bindingResult.hasErrors()) {
			objectErrors = bindingResult.getAllErrors().stream().collect(Collectors.toList());
		} else {
			try {
				defaultSettingService.saveOrUpdateMetalDetectorSetting(metalDetectorDTO);
			} catch (Exception e) {
				ObjectError error = new ObjectError("errorSave", "Can't save");
				objectErrors.add(error);
				LOGGER.error("ERROR while save DefaultSetting: " + e.getMessage());
			}
		}
		return objectErrors;
	}

	/**
	 * Function get default setting from table default_setting default_setting
	 * 
	 * @return Object Json
	 */
	@GetMapping(path = "/getDefaultSetting")
	@ResponseBody
	public MetalDetectorDTO getDefaultSetting() {
		LOGGER.info("Function getDefaultSetting of class MetalDetectorController.");

		MetalDetectorDTO metalDetectorDTO = new MetalDetectorDTO();
		try {
			metalDetectorDTO = defaultSettingService
					.getMetalDetectorDTOByPrefix(Constants.DEFAULT_PREFIX_METAL_DETECTOR);
			LOGGER.info("getMetalDetectorDTOByPrefix value: " + metalDetectorDTO);
		} catch (Exception e) {
			LOGGER.error("ERROR while getDefaultSetting: " + e.getMessage());
		}
		return metalDetectorDTO;
	}

	/**
	 * Function get default setting from table default_setting default_setting
	 * 
	 * @return Object Json
	 */
	@GetMapping(path = "/setDefaultSetting/{id}")
	@ResponseBody
	public String setDefaultSetting(@PathVariable("id") Integer id) {
		LOGGER.info("Function setDefaultSetting of class MetalDetectorController.");

		try {
			MetalDetector metalDetector = metalDetectorService.getMetalDetectorById(id);
			if (metalDetector.getId() == null) {
				return Constants.FAILED;
			} else {
				defaultSettingService.saveOrUpdateMetalDetectorSetting(metalDetector);
				return Constants.SUCCESS;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR while getDefaultSetting: " + e.getMessage());
			return Constants.FAILED;
		}

	}

	/**
	 * Function get MetalDetector By Id and parse to object MetalDetectorDTO.
	 * 
	 * @param id
	 * @return Json MetalDetectorDTO
	 */
	@PostMapping(value = "/getById/{id}")
	@ResponseBody
	public MetalDetectorDTO getMetalDetectorById(@PathVariable("id") Integer id) {
		LOGGER.info("Function getMetalDetectorById of class MetalDetectorController.");

		MetalDetector metalDetector = metalDetectorService.getMetalDetectorById(id);
		MetalDetectorDTO metalDetectorDTO = new MetalDetectorDTO();
		metalDetectorDTO = metalDetectorService.setAttributeMetalDetectorDTO(metalDetector, metalDetectorDTO);

		return metalDetectorDTO;
	}

	/**
	 * Function delete record of table metal detector by id
	 * 
	 * @param metalDetectorId
	 * @return String. Success or fail.
	 */
	@GetMapping(value = "/deleteById/{id}")
	@ResponseBody
	public String deleteMetalDetectorById(@PathVariable("id") Integer metalDetectorId) {
		try {
			if (metalDetectorService.deleteMetalDetectorById(metalDetectorId)) {
				return Constants.SUCCESS;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR delete metal detector by id: " + e.getMessage());
		}
		return Constants.FAILED;
	}

	@ModelAttribute("departments")
	public List<Department> getAllDepartment() {
		return departmentService.getAllDepartment();
	}

	@ModelAttribute("typeProducts")
	public List<TypeProduct> getAllTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	@ModelAttribute("productLots")
	public List<ProductLot> getAllProductLot() {
		return productLotSevice.getAllProductLot();
	}

	@ModelAttribute("employees")
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}

}
