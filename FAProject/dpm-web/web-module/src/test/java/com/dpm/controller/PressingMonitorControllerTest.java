package com.dpm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author LongVT7
 */
public class PressingMonitorControllerTest {

	private MockMvc mvc;

	@InjectMocks
	private PressingMornitorController controller;

	@Mock
	private PressingMornitorService pressingMornitorService;

	@Mock
	private MachineService machineService;

	@Mock
	private TypeProductService typeProductService;

	@Mock
	private IngredientBatchService ingredientBatchService;

	@Mock
	private EmployeeService employeeService;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private BindingResult bindingResult;
	
	@Mock
	private RedirectAttributes redirectAttr;
	
	@Mock
	private ModelMap modelMap;
	
	@Mock
	private Model model;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;

	private static final String URL = "/pressing-monitor";


	//Initial data
	private Page<PressingMornitor> listPressingMonitor = new PageImpl(new ArrayList<PressingMornitor>());
	private List<Machine> listMachine = new ArrayList<Machine>();
	private List<TypeProduct> listTypeProducts = new ArrayList<TypeProduct>();
	private List<IngredientBatch> listIngreBatchs = new ArrayList<IngredientBatch>();
	private Employee employee = new Employee();
	private TypeProduct typeProduct = new TypeProduct();
	private Machine machine = new Machine();
	private IngredientBatch ib = new IngredientBatch();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();

		employee.setId(1);
		typeProduct.setId(1);
		machine.setId(1);
		ib.setId(1);
	}
	
	@Test
	public void indexTest_shouldReturnStatusOk_WhenCallUrl() {
		
		Pageable pageable = PageRequest.of(0, 5, Sort.by("inputTime").descending());
		
		when(authentication.getName()).thenReturn("admin");
		when(employeeService.getEmployeeByUsername("admin")).thenReturn(employee);
		when(pressingMornitorService.getDefaultValue()).thenReturn(new PressingMornitor(1, typeProduct, LocalDateTime.now(), machine, ib, 300, true, 200, true, 150, 50, "", 0));
		when(typeProductService.getAllTypeProduct()).thenReturn(listTypeProducts);
		when(machineService.findAllByMachineType(Constants.MACHINE_TYPE.PRESSING)).thenReturn(listMachine);
		when(ingredientBatchService.fetchAll()).thenReturn(listIngreBatchs);
		when(pressingMornitorService.findAllByDay(LocalDate.now(), pageable)).thenReturn(listPressingMonitor);
		
		ModelAndView modelAndView = controller.index(modelMap, 0, 5, LocalDate.now());
		assertEquals("pressing-monitor", modelAndView.getViewName());
		
	}

	@Test
	public void createNewPressMonitorTest_ShouldReturnOk_WhenCallUrlAndValidData(){
		PressingMonitorDTO dto = new PressingMonitorDTO(0, typeProduct.getId(), LocalDate.now(), LocalTime.now(), machine.getId(), ib.getId(), 200, true, 200, true, 150, 50, "");
		
		when(pressingMornitorService.insert(dto)).thenReturn(true);

		RedirectView redirectView = controller.createNewPressMonitor(dto, redirectAttr, bindingResult);
		
		assertEquals("/pressing-monitor", redirectView.getUrl());
	}

	@Test
	public void createNewPressMonitorTest_ShouldReturnOk_WhenCallUrlAndInValidData(){
		PressingMonitorDTO dto = new PressingMonitorDTO();
		
		when(pressingMornitorService.insert(dto)).thenReturn(false);

		RedirectView redirectView = controller.createNewPressMonitor(dto, redirectAttr, bindingResult);
		
		assertEquals("/pressing-monitor", redirectView.getUrl());
	}

	
	@Test
	public void updatePressMonitorTest_ShouldReturnNotNull_WhenCallUrlAndValidData_methodGet(){
		PressingMornitor pressingMornitor = new PressingMornitor(1, typeProduct, LocalDateTime.now(), machine, ib, 300, true, 200, true, 150, 50, "", 0);
		pressingMornitor.setCreateEmployee(employee);

		Optional<PressingMornitor> optional = Optional.ofNullable(pressingMornitor);

		when(pressingMornitorService.getById(1)).thenReturn(optional);

		PressingMonitorDTO returnData = controller.updatePressMonitor(1);
		
		assertFalse((returnData == null));
	}

	@Test
	public void updatePressMonitorTest_ShouldReturnNull_WhenCallUrlAndInValidData_methodGet(){

		Optional<PressingMornitor> optional = Optional.ofNullable(null);

		when(pressingMornitorService.getById(1)).thenReturn(optional);

		PressingMonitorDTO returnData = controller.updatePressMonitor(1);
		
		assertTrue((returnData == null));
	}

	@Test
	public void updatePressMonitorTest_ShouldReturnNull_WhenCallUrl_methodPost(){
		when(pressingMornitorService.update(new PressingMonitorDTO())).thenReturn(true);
		RedirectView updatePressMonitor = controller.updatePressMonitor(new PressingMonitorDTO(), redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void deletePressMonitorTest_ShouldReturnTrue_WhenCallUrl(){
		when(pressingMornitorService.delete(1)).thenReturn(true);
		RedirectView updatePressMonitor = controller.deletePressMonitor(1, redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void updateDefaultValueTest_ShouldReturnTrue_WhenCallUrl(){
		when(pressingMornitorService.setDefaultValueById(1)).thenReturn(true);
		RedirectView updatePressMonitor = controller.updateDefaultValue(1, redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void updateDefaultValueTest_ShouldReturnFalse_WhenCallUrl(){
		when(pressingMornitorService.setDefaultValueById(1)).thenReturn(false);
		RedirectView updatePressMonitor = controller.updateDefaultValue(1, redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void updateDefaultValueByEntityTest_ShouldReturnTrue_WhenCallUrl(){

		PressingMornitor pressingMornitor = new PressingMornitor(1, typeProduct, LocalDateTime.now(), machine, ib, 300, true, 200, true, 150, 50, "", 0);
		pressingMornitor.setCreateEmployee(employee);

		when(pressingMornitorService.setDefaultValueByEntity(pressingMornitor)).thenReturn(true);
		RedirectView updatePressMonitor = controller.updateDefaultValue(new PressingMonitorDTO(pressingMornitor), redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void updateDefaultValueByEntityTest_ShouldReturnFalse_WhenCallUrl(){

		PressingMornitor pressingMornitor = new PressingMornitor(1, typeProduct, LocalDateTime.now(), machine, ib, 300, true, 200, true, 150, 50, "", 0);
		pressingMornitor.setCreateEmployee(employee);

		when(pressingMornitorService.setDefaultValueByEntity(pressingMornitor)).thenReturn(false);
		RedirectView updatePressMonitor = controller.updateDefaultValue(new PressingMonitorDTO(pressingMornitor), redirectAttr);
		assertEquals("/pressing-monitor", updatePressMonitor.getUrl());
	}

	@Test
	public void approveTest_ShouldReturnTrue_WhenCallUrl(){
		Integer[] ids = new Integer[3];

		when(pressingMornitorService.approve(ids)).thenReturn(true);
		when(request.getHeader("referer")).thenReturn("/");

		RedirectView updatePressMonitor = controller.approve(ids, request, redirectAttr);
		assertEquals("/", updatePressMonitor.getUrl());
	}

	@Test
	public void approveTest_ShouldReturnFalse_WhenCallUrl(){
		Integer[] ids = new Integer[3];

		when(pressingMornitorService.approve(ids)).thenReturn(false);
		when(request.getHeader("referer")).thenReturn("/");

		RedirectView updatePressMonitor = controller.approve(ids, request, redirectAttr);
		assertEquals("/", updatePressMonitor.getUrl());
	}

	@Test
	public void refuseTest_ShouldReturnTrue_WhenCallUrl(){
		Integer[] ids = new Integer[3];

		when(pressingMornitorService.refuse(ids)).thenReturn(true);
		when(request.getHeader("referer")).thenReturn("/");

		RedirectView updatePressMonitor = controller.refuse(ids, request, redirectAttr);
		assertEquals("/", updatePressMonitor.getUrl());
	}

	@Test
	public void refuseTest_ShouldReturnFalse_WhenCallUrl(){
		Integer[] ids = new Integer[3];

		when(pressingMornitorService.refuse(ids)).thenReturn(false);
		when(request.getHeader("referer")).thenReturn("/");

		RedirectView updatePressMonitor = controller.refuse(ids, request, redirectAttr);
		assertEquals("/", updatePressMonitor.getUrl());
	}

	@Test
	public void reportApprovePageTest_ShouldReturnViewName_WhenCallUrlAndAllStatus(){
		Sort sortable = Sort.by("inputTime").descending();
		Pageable pageable = PageRequest.of(1, 5, sortable);

		when(pressingMornitorService.findByInputTimeBetween(LocalDate.now(), LocalDate.now(), pageable)).thenReturn(listPressingMonitor);
		when(pressingMornitorService.findAllByStatusAndInputTimeBetween(1, LocalDate.now(), LocalDate.now(), pageable)).thenReturn(listPressingMonitor);

		ModelAndView reportApprovePage = controller.reportApprovePage(modelMap, LocalDate.now(), LocalDate.now(), 1, 5, -1);
		assertEquals("pressing-monitor-app-rep", reportApprovePage.getViewName());
	}

	@Test
	public void reportApprovePageTest_ShouldReturnViewName_WhenCallUrlWithStatus(){
		Sort sortable = Sort.by("inputTime").descending();
		Pageable pageable = PageRequest.of(1, 5, sortable);

		when(pressingMornitorService.findByInputTimeBetween(LocalDate.now(), LocalDate.now(), pageable)).thenReturn(listPressingMonitor);
		when(pressingMornitorService.findAllByStatusAndInputTimeBetween(1, LocalDate.now(), LocalDate.now(), pageable)).thenReturn(listPressingMonitor);

		ModelAndView reportApprovePage = controller.reportApprovePage(modelMap, LocalDate.now(), LocalDate.now(), 1, 5, 1);

		assertEquals("pressing-monitor-app-rep", reportApprovePage.getViewName());
	}

}
