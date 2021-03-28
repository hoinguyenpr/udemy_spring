package com.dpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpm.constant.Constants;
import com.dpm.service.DepartmentService;
import com.dpm.service.EmployeeService;
import com.dpm.service.MachineService;
import com.dpm.service.ScaleCalibrationService;
import com.dpm.service.ScaleSymbolService;

/**
 * 
 * @author ThuanLV6
 *
 */
@Controller
@RequestMapping("/scale-calibration")
public class ScaleCalibrationController {

	// view control

	
	@Autowired
	private MachineService machineService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ScaleSymbolService scaleSymbolService;
	
	@Autowired
	private ScaleCalibrationService scaleCalibrationService;

	
	// Update by LongVT7: Update syntax constants (22:32 26/01/2021)
	@RequestMapping
	public String onLoad(Model model) {
//		List<ScaleCalibration> list = scaleCalibrationService.findAll();

		model.addAttribute("scales",
				machineService.findAllByMachineType(Constants.MACHINE_TYPE.SCALE));
		model.addAttribute("departments",
				departmentService.findAllByDepartmentCode(Constants.DEPARTMENT_TYPE.SCALE));
		model.addAttribute("employees", employeeService
				.getAllByDepartment(Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION));
		model.addAttribute("standardDevices",
				machineService.findAllByMachineType(Constants.MACHINE_TYPE.WEIGHT));
		model.addAttribute("scaleSymbols", scaleSymbolService.findAll());
		return "scale-calibration";
	}
	
	// test
	@RequestMapping("/testapi")
	@ResponseBody
	public String welcome() {
		return "Welcome to RestTemplate Example.";
	}

	// ThuanLV6 starts initializing data for scale-calibration form
//	@PostConstruct
//	public void initData() {
//	
//
//	}

	// ThuanLV 6 finishes initializing data

}
