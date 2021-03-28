package com.dpm.controller;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dpm.entity.Department;
import com.dpm.entity.Employee;
import com.dpm.entity.Machine;
import com.dpm.entity.ScaleCalibration;
import com.dpm.service.EmployeeService;
import com.dpm.service.ScaleCalibrationService;

/**
 * 
 * @author ThuanLV6
 * For testing ScaleCalibrationRestController
 * 19-02-2021
 *
 */


@RunWith(SpringRunner.class)
@WebMvcTest(value = ScaleCalibrationRestController.class)
@WithMockUser
public class ScaleCalibrationRestControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	// MockMvc is the main entry point for server-side Spring MVC test support
	
	@MockBean
	private ScaleCalibrationService scaleCalibrationService;
	
//	@MockBean
//	private EmployeeService employeeService;
	
	private Machine mockStandardDevice = new Machine(
			1, "Qua ta 5kg", "weight5", "none",
		"China", "Available","19/02/2021",
			"none", 10
			);
	private Machine mockCalibratedDevice = new Machine(
			2, "Can dien tu 5kg", "scale5", "C-01",
		"China", "Available","19/02/2021",
			"none", 10
			);
	
	private Department mockDepartment = new Department("dep1","Team chuan can", "ThuanLV6");

	private Employee mockCalibrator = new Employee(100, "Thomas Shelby", "thomas100", "none",
			"none", "none");
	private Employee mockInspector = new Employee(101, "Finn Shelby", "finn101", "none",
			"none", "none");
	
	
	private ScaleCalibration mockScaleCalibration = new ScaleCalibration(
			LocalDateTime.now(), "Manual", 1,
			1, 1, 1, "A little comment",
			mockStandardDevice, mockCalibratedDevice, mockDepartment,
			mockCalibrator, mockInspector, "Pending"
			);
	
	@Test
	public void testGetScaleCalibrationById() throws Exception {

		Mockito.when(
				scaleCalibrationService.findScaleCalibrationById(Mockito.anyInt())).thenReturn(mockScaleCalibration);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/scale-calibration/api").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("ahihi"+result.getResponse());
		String expected = "{id:Course1,name:Spring,description:10Steps}";

		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	
}
