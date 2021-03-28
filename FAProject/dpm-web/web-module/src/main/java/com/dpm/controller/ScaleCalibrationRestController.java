package com.dpm.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpm.constant.Constants;
import com.dpm.dto.ScaleCalibrationDto;
import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.Employee;
import com.dpm.entity.ScaleCalibration;
import com.dpm.entity.ScaleCalibrationAjaxResponseBody;
import com.dpm.service.DepartmentService;
import com.dpm.service.EmployeeService;
import com.dpm.service.MachineService;
import com.dpm.service.ScaleCalibrationService;
import com.dpm.service.ScaleSymbolService;
import com.dpm.utility.ScaleCalibrationUtil;

@RestController
@RequestMapping("scale-calibration/api")
public class ScaleCalibrationRestController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScaleCalibrationRestController.class);
	/**
	 * rest controller for scale calibration ThuanLV6
	 * 
	 * @return
	 */

	@Autowired
	private ScaleCalibrationService scaleCalibrationService;

	@Autowired
	private MachineService machineService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ScaleSymbolService scaleSymbolService;

	@RequestMapping("/get-all")
	@GetMapping
	public List<ScaleCalibration> getAll() {
		return scaleCalibrationService.findAll();
	}

	@GetMapping(value = "/{id}")
	public ScaleCalibration findById(@PathVariable("id") int id) {
		return scaleCalibrationService.findScaleCalibrationById(id);
	}

	@PostMapping(value = "/save")
	public ScaleCalibrationAjaxResponseBody save(@RequestBody ScaleCalibrationDto dto) {

		// create entity object from Dto
		ScaleCalibration scaleCalibration = new ScaleCalibration();
		if (dto.getId() != null) {
			scaleCalibration.setId(dto.getId());
		}
		LocalDateTime createdDate;
		if (dto.getCreatedDate() == null) {
			createdDate = LocalDateTime.now();
			createdDate = createdDate.truncatedTo(ChronoUnit.MINUTES);

		} else {
			createdDate = (dto.getCreatedDate());
		}
		scaleCalibration.setCreatedDate(createdDate);
		if (dto.getCalibratedDeviceId() != null) {
			scaleCalibration.setCalibratedDevice(
					machineService.findMachineById(dto.getCalibratedDeviceId()));
		}
		scaleCalibration.setDepartment(
				departmentService.getDepartmentById(dto.getDepartmentCode()));
		scaleCalibration.setStandardDevice(
				machineService.findMachineById(dto.getStandardDeviceId()));
		scaleCalibration
				.setCalibrator(employeeService.getEmployeeById(dto.getCalibratorId()));
		scaleCalibration.setCalibratingMethod(dto.getCalibratingMethod());
		scaleCalibration
				.setScaleSymbol(scaleSymbolService.findById(dto.getScaleSymbolId()));
		scaleCalibration.setFirstResult(dto.getFirstResult());
		scaleCalibration.setSecondResult(dto.getSecondResult());
		scaleCalibration.setThirdResult(dto.getThirdResult());
		scaleCalibration.setAvarageResult(
				(dto.getFirstResult() + dto.getSecondResult() + dto.getThirdResult())
						/ 3);
		scaleCalibration.setComment(dto.getComment());
		LOGGER.info("ThuanLV6INFO: " + dto.toString());
		scaleCalibrationService.saveScaleCalibration(scaleCalibration);
		// Create Response Object
		ScaleCalibrationAjaxResponseBody response = new ScaleCalibrationAjaxResponseBody(
				"Done", scaleCalibration);
		return response;
	}

	@PostMapping(value = "/delete/{id}")
	public ScaleCalibrationAjaxResponseBody deleteById(@PathVariable("id") int id) {
		ScaleCalibrationAjaxResponseBody response = new ScaleCalibrationAjaxResponseBody(
				"Done", scaleCalibrationService.deleteByStatus(id));
		return response;
	}

	// get data with paging
	@RequestMapping("/get-page")
	@GetMapping
	public Page<ScaleCalibration> getPage(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "amount", required = false, defaultValue = "10") int amount) {

		Pageable pageable = PageRequest.of(pageNumber, amount,
				Sort.by("id").descending());
		Page<ScaleCalibration> scaleCalibrationPage = scaleCalibrationService
				.getPage(pageable);
		return scaleCalibrationPage;
	}

	// get all calibrator

	@GetMapping("/get-calibrators-list")
	public List<Employee> getCalibratorsList() {
		return employeeService
				.getAllByDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION);
	}

	// get data with paging
	@PostMapping("/get-page-with-filter")
	public Page<ScaleCalibration> getPageWithFilter(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "amount", required = false, defaultValue = "10") int amount,
			@RequestBody ScaleCalibrationFilterDto filterDto) {

		Pageable pageable = PageRequest.of(pageNumber, amount,
				Sort.by("id").descending());
//		Page<ScaleCalibration> scaleCalibrationPage = scaleCalibrationService.getPage(pageable);
		Page<ScaleCalibration> result = scaleCalibrationService
				.getPageWithSearchAndFilter(filterDto, pageable);
		return result;
	}

	// get data with paging
	@GetMapping("/export")
	public ScaleCalibrationAjaxResponseBody export(
			@RequestParam(value = "fromDate", required = true, defaultValue = "0") String fromDate, 
			@RequestParam(value = "toDate", required = true, defaultValue = "0") String toDate, 
			@RequestParam(value = "calibratorId", required = false, defaultValue = "0") int calibratorId, 
			@RequestParam(value = "inspectorId", required = false, defaultValue = "0") int inspectorId, 
			@RequestParam(value = "isApproved", required = false, defaultValue = "false") boolean isApproved, 
			@RequestParam(value = "isPending", required = false, defaultValue = "false") boolean isPending, 
			@RequestParam(value = "isRejected", required = false, defaultValue = "false") boolean isRejected, 
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
		
			
			HttpServletResponse response) throws IOException {
		
			ScaleCalibrationFilterDto filterDto = new ScaleCalibrationFilterDto();
			if (fromDate.equals("0")) {
				filterDto.setStartDate(null);
			}else {
				filterDto.setStartDate(LocalDateTime.parse(fromDate));
			}
			if (toDate.equals("0")) {
				filterDto.setEndDate(null);
			}else {
				filterDto.setEndDate(LocalDateTime.parse(toDate));
			}
			
			filterDto.setCalibratorId(calibratorId);
			filterDto.setInspectorId(inspectorId);
			filterDto.setApproved(isApproved);
			filterDto.setRejected(isRejected);
			filterDto.setPending(isPending);
			filterDto.setKeyword(keyword);
			
			LOGGER.debug(filterDto.toString());
			
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=scale_calibration_" + currentDateTime + ".xlsx";
	        List<ScaleCalibration> listScaleCalibrations = scaleCalibrationService.getReportWithSearchAndFilter(filterDto);
	        response.setHeader(headerKey, headerValue);
	        ScaleCalibrationUtil excelExporter = new ScaleCalibrationUtil(listScaleCalibrations);
	       
	        excelExporter.export(response);    
	        return new ScaleCalibrationAjaxResponseBody("Done", null);
	      
	}

}
