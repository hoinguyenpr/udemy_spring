package com.dpm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.dao.SieveDryingCustomDAO;
import com.dpm.dao.SieveDryingDAO;
import com.dpm.dto.SieveDryingDTO;
import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.DefaultSetting;
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

public class SieveDryingControllerTest {
	
	@InjectMocks
	private SieveDryingController sieveDryingController;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private ProductLotSevice lotSevice;
	@Mock
	private IngredientBatchService matService;
	@Mock
	private TypeProductService typeProductService;
	@Mock
	private DefaultSettingService defSettingService;
	@Mock
	private SieveDryingService sieveDryingService;
	@Mock
	private MachineService machineService;
	@Mock
	private IngredientBatchService ingredientBatchService;
	@Mock
	private SieveDryingDAO sieveDryingDAO;
	@Mock
	private ModelMap modelMap;
	@Mock
	private SieveDryingReportDTO defaultReportDTO;
	@Mock
	private SieveDryingCustomDAO sdCustomDao;
	@Mock
	private RedirectAttributes redirectAttr;
	private MockMvc mockMvc;
	
	private static final String URL = "/sieve-drying";
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(sieveDryingController).build();

	}
	
	//Test for route /sieve-drying-input
	@Test
	public void newRecordTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception {
		String url = URL + "/sieve-drying-input";

		List<Employee> listEmployee = new ArrayList<Employee>();
		List<ProductLot> productLots = new ArrayList<ProductLot>();
		List<IngredientBatch> matLot = new ArrayList<IngredientBatch>();
		List<IngredientBatch> igrBatchs = new ArrayList<IngredientBatch>();
		List<TypeProduct> productList = new ArrayList<TypeProduct>();
		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		SieveDrying defaultSieveDrying = new SieveDrying();
		List<Machine> machineList = new ArrayList<Machine>();
		int totalPage = 0;
		
		Sort sortable = Sort.by("id").descending();
		List<SieveDrying> list = Arrays.asList(new SieveDrying(), new SieveDrying());
		Pageable pageable = PageRequest.of(0, 5, sortable);
		Page<SieveDrying> sieveDryingList = new PageImpl<>(list, pageable, list.size());
		
		when(employeeService.findByPosition(EmployeePosition.Qc.toString())).thenReturn(listEmployee);
		when(employeeService.findByPosition(EmployeePosition.Worker.toString())).thenReturn(listEmployee);
		when(employeeService.findByPosition(EmployeePosition.Verifier.toString())).thenReturn(listEmployee);
		when(lotSevice.getAllProductLot()).thenReturn(productLots);
		when(matService.getAll()).thenReturn(matLot);
		when(ingredientBatchService.fetchAll()).thenReturn(igrBatchs);
		when(typeProductService.getAllTypeProduct()).thenReturn(productList);
		when(defSettingService.getDefaultSieveDrying()).thenReturn(defaultSieveDrying);
		when(machineService.findAllMachine()).thenReturn(machineList);
		when(sieveDryingDAO.getAllRecordsByDate(pageable, LocalDateTime.now().toLocalDate())).thenReturn(sieveDryingList);
		
		ModelAndView view = sieveDryingController.newRecord(modelMap, 0, 5);
		assertEquals("sieve-drying-input", view.getViewName());
//		System.out.println(view);
//		mockMvc.perform(get(url)).andExpect(view().name("sieve-drying-input"));

	}
	
	//Test for route /sieve-drying-appr
	@Test
	public void approveRecordTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception {
		String url = URL + "/sieve-drying-appr";

			
		List<Machine> machineList = new ArrayList<Machine>();
		int totalPage = 0;
		
		Sort sortable = Sort.by("id").descending();
		List<SieveDrying> list = Arrays.asList(new SieveDrying(), new SieveDrying());
		Pageable pageable = PageRequest.of(0, 5, sortable);
		Page<SieveDrying> sieveDryingList = new PageImpl<>(list, pageable, list.size());
		
		when(sieveDryingDAO.getPending(pageable)).thenReturn(sieveDryingList);
		when(sieveDryingDAO.getApproved(pageable)).thenReturn(sieveDryingList);
		when(sieveDryingDAO.getApprovedAndPending(pageable)).thenReturn(sieveDryingList);
		
		ModelAndView view = sieveDryingController.approveRecord(modelMap, 0, 5, 0);
		assertEquals("sieve-drying-appr", view.getViewName());
//		System.out.println(view);
//		mockMvc.perform(get(url)).andExpect(view().name("sieve-drying-input"));
	}
		
	//Test for route /sieve-drying-report
	@Test
	public void reportRecordTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception {
				
		Sort sortable = Sort.by("input_date").descending();
		List<SieveDrying> list = Arrays.asList(new SieveDrying(), new SieveDrying());
		Pageable pageable = PageRequest.of(0, 5, sortable);
		Page<SieveDrying> sieveDryingList = new PageImpl<>(list, pageable, list.size());
		
		when(sieveDryingDAO.getAllButNotDefaultForReport(pageable)).thenReturn(sieveDryingList);
		
		ModelAndView view = sieveDryingController.reportRecord(modelMap, 0, 5);
		assertEquals("sieve-drying-report", view.getViewName());
//		System.out.println(view);
//		mockMvc.perform(get(url)).andExpect(view().name("sieve-drying-input"));
	}
	
	//Test for route /get-report
		@Test
	public void getReportTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception {
					
			Sort sortable = Sort.by("input_date").descending();
			List<SieveDrying> list = Arrays.asList(new SieveDrying(), new SieveDrying());
			Pageable pageable = PageRequest.of(0, 5, sortable);
			Page<SieveDrying> sieveDryingList = new PageImpl<>(list, pageable, list.size());
			SieveDryingReportDTO rdto = new SieveDryingReportDTO();
			
			when(sdCustomDao.getReport(rdto, pageable)).thenReturn(sieveDryingList);
			
			ModelAndView view = sieveDryingController.getReport(rdto, modelMap, 0, 5);
			assertEquals("sieve-drying-report", view.getViewName());
//			System.out.println(view);
//			mockMvc.perform(get(url)).andExpect(view().name("sieve-drying-input"));
		}
		
	//Test for route /create
		@Test
	public void createTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			
			SieveDrying sieveDrying = new SieveDrying();
			String results = "SUCCESS";
			SieveDryingDTO dto = new SieveDryingDTO();
			Optional<Employee> employee = Optional.of(new Employee(1,"name","worker","department", "manager","note"));
			Optional<ProductLot> lotCode = Optional.of(new ProductLot());
			Optional<TypeProduct> typeProduct = Optional.of(new TypeProduct());
			Optional<IngredientBatch> igrBatch = Optional.of(new IngredientBatch());
			Optional<Machine> machine = Optional.of(new Machine());
			
			
			
			Sort sortable = Sort.by("input_date").descending();
			List<SieveDrying> list = Arrays.asList(new SieveDrying(), new SieveDrying());
			Pageable pageable = PageRequest.of(0, 5, sortable);
			Page<SieveDrying> sieveDryingList = new PageImpl<>(list, pageable, list.size());
			
			when(employeeService.get(1)).thenReturn(employee);
			when(lotSevice.get(1)).thenReturn(lotCode);
			when(typeProductService.get(1)).thenReturn(typeProduct);
			when(ingredientBatchService.get(1)).thenReturn(igrBatch);
			when(machineService.get(1)).thenReturn(machine);
			when(sieveDryingService.createStr(sieveDrying)).thenReturn(results);
			
			RedirectView testView = sieveDryingController.create(dto, redirectAttr);
			assertEquals("/sieve-drying/sieve-drying-input", testView.getUrl());
		}
		
	//Test for route /update
		@Test
	public void updateTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			String results = "SUCCESS";
			SieveDryingDTO dto = new SieveDryingDTO(1,1,"2021-01-01","00:00",1,1,1f,1f,1f,1f,1f,true,true,true,true,true,true,1,"Pending",1,1,1);
			
			when(sieveDryingService.isValid(dto)).thenReturn(true);
			when(sieveDryingService.updateSieveDrying(dto)).thenReturn(results);
			
			RedirectView testView = sieveDryingController.update(dto, redirectAttr);
			assertEquals("/sieve-drying/sieve-drying-input", testView.getUrl());
		}
	
	//Test for route /delete
	@Test
	public void deleteTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			String results = "SUCCESS";
			
			when(sieveDryingService.deleteSieveDrying(1)).thenReturn(results);
			
			RedirectView testView = sieveDryingController.delete(1, redirectAttr);
			assertEquals("/sieve-drying/sieve-drying-input", testView.getUrl());
		}
	
	//Test for route /approve
		@Test
	public void approveTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			SieveDryingReportDTO rdto = new SieveDryingReportDTO(1, 1, LocalDate.now(), LocalDate.now(), 1, 1, 1,1);
			String approveResult = "SUCCESS";
			Sort sortable = Sort.by("input_date").descending();
			Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
			Pageable pageable = PageRequest.of(0, 5, sortable);
			
			when(sdCustomDao.getReport(rdto, pageable)).thenReturn(list);
			
			ModelAndView view = sieveDryingController.approve(rdto, modelMap, 0, 5, 1);
			assertEquals("sieve-drying-report", view.getViewName());
		}
		
	//Test for route /approve
			@Test
	public void rejectTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
				SieveDryingReportDTO rdto = new SieveDryingReportDTO(1, 1, LocalDate.now(), LocalDate.now(), 1, 1, 1,1);
				String approveResult = "SUCCESS";
				Sort sortable = Sort.by("input_date").descending();
				Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
				Pageable pageable = PageRequest.of(0, 5, sortable);
				
				when(sdCustomDao.getReport(rdto, pageable)).thenReturn(list);
				
				ModelAndView view = sieveDryingController.reject(rdto, modelMap, 0, 5, 1);
				assertEquals("sieve-drying-report", view.getViewName());
			}
		
	//Test for route /approve-selected
		@Test
	public void approveSelectedTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			String approveResult = "SUCCESS";
			SieveDryingReportDTO rdto = new SieveDryingReportDTO(1, 1, LocalDate.now(), LocalDate.now(), 1, 1, 1,1);
			List<Integer> idList = new ArrayList<Integer>();
			idList.add(1);
			Sort sortable = Sort.by("input_date").descending();
			Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
			Pageable pageable = PageRequest.of(0, 5, sortable);
			
			when(sieveDryingService.approveSieveDrying(idList)).thenReturn(approveResult);
			when(sdCustomDao.getReport(rdto, pageable)).thenReturn(list);
			
			ModelAndView view = sieveDryingController.approveSelected(rdto, modelMap, 0, 5, idList);
			assertEquals("sieve-drying-report", view.getViewName());
		}
	
	//Test for route /approve-selected
		@Test
	public void rejectSelectedTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			String approveResult = "SUCCESS";
			SieveDryingReportDTO rdto = new SieveDryingReportDTO(1, 1, LocalDate.now(), LocalDate.now(), 1, 1, 1,1);
			List<Integer> idList = new ArrayList<Integer>();
			idList.add(1);
			Sort sortable = Sort.by("input_date").descending();
			Page<SieveDrying> list = new PageImpl<SieveDrying>(new ArrayList<SieveDrying>());
			Pageable pageable = PageRequest.of(0, 5, sortable);
			
			when(sieveDryingService.rejectSieveDrying(idList)).thenReturn(approveResult);
			when(sdCustomDao.getReport(rdto, pageable)).thenReturn(list);
			
			ModelAndView view = sieveDryingController.rejectSelected(rdto, modelMap, 0, 5, idList);
			assertEquals("sieve-drying-report", view.getViewName());
		}
	
	//Test for /set-default
		@Test
	public void configTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
			String results = "SUCCESS";
			SieveDryingDTO dto = new SieveDryingDTO(1,1,"2021-01-01","00:00",1,1,1f,1f,1f,1f,1f,true,true,true,true,true,true,1,"Pending",1,1,1);
			
			when(defSettingService.setDefaultSieveDrying(sieveDryingService.getSieveDryingById(1))).thenReturn(results);
			when(sieveDryingService.setDefault(dto)).thenReturn(results);
			
			RedirectView testView = sieveDryingController.config(dto, redirectAttr);
			assertEquals("/sieve-drying/sieve-drying-input", testView.getUrl());
		}
		
	//Test for /get-report/download
	public void reportDownloadTest_ShouldReturnCorrectView_WhenCallUrl() throws Exception{
		
	}
}
