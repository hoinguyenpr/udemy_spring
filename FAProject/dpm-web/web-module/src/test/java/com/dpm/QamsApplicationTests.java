package com.dpm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dpm.constant.Constants;
import com.dpm.dao.ScaleCalibrationCustomDao;
import com.dpm.dao.ScaleCalibrationDAO;
import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.Employee;
import com.dpm.entity.ScaleCalibration;
import com.dpm.entity.ScaleSymbol;
import com.dpm.service.EmployeeService;
import com.dpm.service.MachineService;
import com.dpm.service.ScaleCalibrationService;
import com.dpm.service.ScaleSymbolService;
import com.dpm.utility.Status;

/**
 * 
 * @author ThuanLV6 modified to config test
 * @author LongVT7: modified format
 */
@SpringBootTest

class QamsApplicationTests {

	@Autowired
	ScaleSymbolService scaleSymbolService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	MachineService machineService;

	@Autowired
	ScaleCalibrationService scaleCalibrationService;

	@Autowired
	ScaleCalibrationDAO scaleCalibrationDAO;

	@Autowired
	ScaleCalibrationCustomDao scaleCalibrationCustomDao;

	@Test
	public void testGetAllScaleSymbol() {
		assertEquals(3, scaleSymbolService.findAll().size());
	}

	@Test
	public void testGetScaleSymbolByString() {
		assertEquals(2, scaleSymbolService.findByName("C-02").getId());
	}

	@Test
	public void testAddScaleSymbol() {
		assertEquals(4, scaleSymbolService.save(new ScaleSymbol("C-03")).getId());
	}

	@Test
	public void testAddEmp() {

		employeeService.saveOrUpdateEmployee(new Employee("Tommy", "tommy123", "Leader",
				Constants.DEPARTMENT_TYPE.SCALE_CALIBRATION, "none", "none"));
		assertEquals(4, employeeService.getAllEmployee().size());
	}

	@Test
	public void testAmountOfScale() {
		assertEquals(0, machineService.findAllByMachineType(Constants.MACHINE_TYPE.SCALE).size());
	}

	@Test
	public void testFindScaleCalibrationById() {
		assertEquals(1.1, scaleCalibrationService.findScaleCalibrationById(2).getAvarageResult());
	}

	@Test
	public void testDeleteScaleCalibrationWithStatus() {
		assertEquals(3, scaleCalibrationService.findAll().size());
	}

	@Test
	public void testDaoPaginationFunctions() {
		Pageable firstPageWithTwoElements = PageRequest.of(1, 15, Sort.by("id").descending());
		Page<ScaleCalibration> scaleCalibrationPage = scaleCalibrationService.getPage(firstPageWithTwoElements);
		for (ScaleCalibration object : scaleCalibrationPage) {
			System.out.println("xxxxxxxx " + object.toString());
		}
		assertEquals(1, 1);
	}

	@Test
	public void testScaleCalibrationFiltering() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
		ScaleCalibrationFilterDto filter = new ScaleCalibrationFilterDto();
		filter.setStartDate(LocalDateTime.parse("1995-11-19T00:00:00"));
		filter.setEndDate(LocalDateTime.parse("1999-05-22T00:00:00"));
		filter.setCalibratorId(4);
		Page<ScaleCalibration> page = scaleCalibrationService.getPageWithSearchAndFilter(filter, pageable);
		for (ScaleCalibration sc : page) {
			System.out.println("ahihihihihihihih" + sc.toString());
		}

		assertEquals(1, 1);
	}

	@Test
	public void testEnumToCollection() {
		List<String> status = Stream.of(Status.values()).map(Enum::name).collect(Collectors.toList());
		for (String s : status) {
			System.out.println(s);
		}
		assertEquals(1, 1);
	}

	@Test
	public void testReportExcel() {
		ScaleCalibrationFilterDto filter = new ScaleCalibrationFilterDto();
		filter.setStartDate(LocalDateTime.parse("1995-11-19T00:00:00"));
//		filter.setEndDate(LocalDateTime.parse("1999-05-22T00:00:00"));
//		filter.setCalibratorId(4);
		for(ScaleCalibration sc: scaleCalibrationService.getReportWithSearchAndFilter(filter)) {
			System.out.println("xxxxx"+sc.toString());
		}
		

		assertEquals(1, 1);
	}

}
