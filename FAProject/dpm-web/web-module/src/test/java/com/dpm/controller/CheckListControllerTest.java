package com.dpm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.dto.CheckListDTO;
import com.dpm.dto.CheckListFilterDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.entity.CheckList;
import com.dpm.entity.DefaultSetting;
import com.dpm.entity.Employee;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;
import com.dpm.service.CheckListExportService;
import com.dpm.service.CheckListSevice;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.EmployeeService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeProductService;
import com.dpm.service.impl.CheckListSeviceImpl;
import com.dpm.utility.EmployeePosition;
import com.dpm.utility.Status;

public class CheckListControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private CheckListController checkListController;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private ProductLotSevice lotSevice;
	@Mock
	private CheckListSevice checkListSevice;
	@Mock
	private TypeProductService typeProductService;
	@Mock
	private DefaultSettingService settingService;
	@Mock
	private CheckListExportService checkListExportService;
	@Mock
	private CheckListSeviceImpl checkListSeviceImpl;
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

	private static final String URL = "/checklist";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(checkListController).build();

	}

	@Test
	public void filterViewTest_shouldRetunStatusOk_WhenCallUrl() throws Exception {
		String url = URL + "/show-check-list";

		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		List<ProductLot> lots = new ArrayList<ProductLot>();

		when(settingService.getDefaultByFeature("qa_check")).thenReturn(defaultSettings);
		when(lotSevice.getAllProductLot()).thenReturn(lots);

		mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@Test
	public void filterViewTest_shouldRetunStatusNotFound_WhenCallUrl() throws Exception {
		String url = URL + "/show-check-list-";

		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		List<ProductLot> lots = new ArrayList<ProductLot>();

		when(settingService.getDefaultByFeature("qa_check")).thenReturn(defaultSettings);
		when(lotSevice.getAllProductLot()).thenReturn(lots);

		mockMvc.perform(get(url)).andExpect(status().isNotFound());

	}

	@Test
	public void createViewTest_shouldRetunStatusOK_WhenCallUrl() throws Exception {
		String url = URL + "/create-check-list";

		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		List<ProductLot> lots = new ArrayList<ProductLot>();

		when(settingService.getDefaultByFeature("qa_check")).thenReturn(defaultSettings);
		when(lotSevice.getAllProductLot()).thenReturn(lots);
		when(checkListSevice.setSort("DESC")).thenReturn(Sort.by("modifyDate").descending());

		mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@Test
	public void approveViewTest_shouldRetunStatusOK_WhenCallUrl() throws Exception {
		String url = URL + "/approval-check-list";

		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		List<ProductLot> lots = new ArrayList<ProductLot>();

		when(settingService.getDefaultByFeature("qa_check")).thenReturn(defaultSettings);
		when(lotSevice.getAllProductLot()).thenReturn(lots);
		when(checkListSevice.setSort("DESC")).thenReturn(Sort.by("modifyDate").descending());

		mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@Test
	public void createTest() throws Exception {
		Employee qc = new Employee("name", "username", "position", "department", "managerment", "note");
		qc.setUsername("lampqt");
		qc.setPassword("123");
		qc.setId(1);
		qc.setRole(null);

		Optional<ProductLot> lot = Optional.of(new ProductLot("lot001", LocalDateTime.now(), 100,
				new TypeProduct("type01", "typeProductName01", "note"), qc));

		CheckListDTO dto = new CheckListDTO(2, "2021-01-01", "00:00 AM", 0.01f, (short) 1, 0.01f, true, true, 0.01f,
				1000, 0, 1000, "remark", "lot001", "lampqt", "");

		CheckList checklist = CheckList.mapping(dto);
		checklist.setId(0);

		Locale loc = new Locale("VI");

		when(employeeService.getEmployeeByUsername(dto.getQc())).thenReturn(qc);
		when(lotSevice.findByLotCode(dto.getLotCode())).thenReturn(lot);
		when(checkListSevice.createCheckList(checklist)).thenReturn("SUCCESS");

		when(checkListSeviceImpl.createCheckList(checklist)).thenReturn("SUCCESS");
		when(request.getHeader("referer")).thenReturn("/");

		RedirectView actual = checkListController.create(dto, bindingResult, redirectAttr, modelMap, request);
		assertEquals("/", actual.getUrl());

	}

	@Test
	public void updateTest() throws Exception {
		Employee qc = new Employee("name", "username", "position", "department", "managerment", "note");
		qc.setUsername("lampqt");
		qc.setPassword("123");
		qc.setId(1);
		qc.setRole(null);

		Optional<ProductLot> lot = Optional.of(new ProductLot("lot001", LocalDateTime.now(), 100,
				new TypeProduct("type01", "typeProductName01", "note"), qc));

		CheckListDTO dto = new CheckListDTO(2, "2021-01-01", "00:00 AM", 0.01f, (short) 1, 0.01f, true, true, 0.01f,
				1000, 0, 1000, "remark", "lot001", "lampqt", "");

		CheckList checklist = CheckList.mapping(dto);
		checklist.setId(0);

		when(employeeService.getEmployeeByUsername(dto.getQc())).thenReturn(qc);
		when(lotSevice.findByLotCode(dto.getLotCode())).thenReturn(lot);
		when(checkListSevice.createCheckList(checklist)).thenReturn("SUCCESS");

		if (checkListController.update(dto, bindingResult, redirectAttr, request) != null) {
			assertTrue(true);
		} else

		{
			assertTrue(false);
		}

	}

	@Test
	public void deleteTest_shouldStateSUCCESS_WhenInputId() throws Exception {
		when(checkListSevice.delete(1)).thenReturn("SUCCESS");

		when(request.getHeader("referer")).thenReturn("/");

		RedirectView r = checkListController.delete(1, redirectAttr, request);
		assertEquals("/", r.getUrl());

//		if (checkListController.delete(1, redirectAttr, request) != null) {
//			
//			
//			assertTrue(true);
//		} else {
//			assertTrue(false);
//		}

	}

	@Test
	public void filterTest() throws Exception {
		Integer pageNum = 2;
		Integer size = 10;
		String sort = "ASC";

		Sort sortable = Sort.by(sort).ascending();
		Pageable pageable = PageRequest.of(pageNum, size, sortable);
		List<CheckList> list = Arrays.asList(new CheckList(), new CheckList());

		Page<CheckList> filters = new PageImpl<>(list, pageable, list.size());

		when(checkListSevice.filter(new CheckListFilterDTO(), pageable)).thenReturn(filters);

		mockMvc.perform(post("/checklist/filter").param("productType", "1").param("lot", "1")
				.param("date", "2021-01-01").param("shift", "00:00:00-23:59:59").param("status", "Pending")
				.param("size", "5").param("addition", "1")).andExpect(status().isOk())
				.andExpect(model().attribute("addition", "1")).andExpect(view().name("check-list-view"));

	}

	@Test
	public void reportTest() throws Exception {

		Employee qc = new Employee("name", "username", "position", "department", "managerment", "note");
		qc.setUsername("lampqt");
		qc.setPassword("123");
		qc.setId(1);
		qc.setRole(null);

		ProductLot lot = new ProductLot("lot001", LocalDateTime.now(), 100,
				new TypeProduct("type001", "type001", "note"), qc);

		CheckList c = new CheckList(LocalDate.now(), LocalTime.now(), (float) 0.01, (short) 1, (float) 0.01, true, true,
				(float) 0.01, 1000, 0, 1000, "", Status.Pending, lot, qc, qc, null);
		CheckList c2 = new CheckList(LocalDate.now(), LocalTime.now().plusHours((long) 1), (float) 0.01, (short) 1,
				(float) 0.01, true, true, (float) 0.01, 1000, 0, 1000, "", Status.Pending, lot, qc, qc, null);
		List<CheckList> list = new ArrayList<CheckList>();
		list.add(c);
		list.add(c2);
		CheckListFilterDTO dto = new CheckListFilterDTO(1, 1, "2021-01-01", "00:00:00-23:59:59", Status.Pending, 5);

		when(checkListSevice.report(dto)).thenReturn(list);

		checkListController.report(model, dto, request, response, redirectAttr);

	}

	@Test
	public void approvalTest() throws Exception {
		when(checkListSevice.approval(1)).thenReturn("SUCCESS");
		when(checkListSevice.approval(2)).thenReturn("SUCCESS");

		when(request.getHeader("referer")).thenReturn("/");
		Integer[] ids = {1,2};
		RedirectView actual = checkListController.approval(request, ids, redirectAttr);
		assertEquals("/", actual.getUrl());

	}
	@Test
	public void rejectTest() throws Exception {
		when(checkListSevice.reject(1)).thenReturn("SUCCESS");
		when(checkListSevice.reject(2)).thenReturn("SUCCESS");

		when(request.getHeader("referer")).thenReturn("/");
		Integer[] ids = {1,2};
		RedirectView actual = checkListController.reject(request, ids, redirectAttr);
		assertEquals("/", actual.getUrl());

	}
	@Test
	public void removesTest() throws Exception {
		when(checkListSevice.remove(1)).thenReturn("SUCCESS");
		when(checkListSevice.remove(2)).thenReturn("SUCCESS");

		when(request.getHeader("referer")).thenReturn("/");
		Integer[] ids = {1,2};
		RedirectView actual = checkListController.removes(request, ids, redirectAttr);
		assertEquals("/", actual.getUrl());

	}

	@Test
	public void getQCsTest() {
		Employee qc1 = new Employee("name", "username", "position", "department", "managerment", "note");
		Employee qc2 = new Employee("name", "username", "position", "department", "managerment", "note");
		
		when(employeeService.findByPosition(EmployeePosition.Qc.toString())).thenReturn(Arrays.asList(qc1,qc2));
		List<Employee> actual = checkListController.getQCs();
		assertEquals(2, actual.size());	

	}
	@Test
	public void getProductLotsTest() {
		when(lotSevice.getAllProductLot()).thenReturn(Arrays.asList(new ProductLot(),new ProductLot(),new ProductLot()));
		List<ProductLot> actual = checkListController.getProductLots();
		assertEquals(3, actual.size());
	}
	@Test
	public void getDefaultSettingTest() {
		CheckListSettingDTO checkListDefault = new CheckListSettingDTO("", "", "", true, true, "", "", "", "", "", "", "");

		
		when(settingService.getDefaultByFeature("qa_check")).thenReturn(new ArrayList<>());
		when(settingService.convert(new ArrayList<>())).thenReturn(checkListDefault);
		
		CheckListSettingDTO actual = checkListController.getDefaultSetting();
		assertEquals(checkListDefault, actual);
		
	}
	@Test
	public void getPagesTest() {
		Page<CheckList> page = new PageImpl<CheckList>(new ArrayList<>());
		when(checkListSevice.getCheckListCurrentDay(PageRequest.of(0, 5))).thenReturn(page);
		
		Page<CheckList> actual = checkListController.getPages();
		
		assertEquals(page, actual);
		
		
	}
}
