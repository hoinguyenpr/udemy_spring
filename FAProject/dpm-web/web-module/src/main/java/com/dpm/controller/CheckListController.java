package com.dpm.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dpm.configs.DataGeneration;
import com.dpm.constant.Constants;
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
import com.dpm.service.impl.CheckListExportServiceImpl;
import com.dpm.utility.EmployeePosition;
import com.dpm.utility.Status;

/**
 * CheckListController use manage action redirect to module checklist
 * 
 * @Author LamPQT
 * 
 */
@Controller
@RequestMapping(value = "/checklist")
public class CheckListController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CheckListController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductLotSevice lotSevice;

	@Autowired
	private CheckListSevice checkListSevice;

	@Autowired
	private TypeProductService typeProductService;

	@Autowired
	private DefaultSettingService settingService;

	@Autowired
	private CheckListExportService checkListExportService;

	@GetMapping(path = "/show-check-list")
	public String filterView(Model model) {

		List<TypeProduct> typeProducts = typeProductService.getAllTypeProduct();
		List<CheckList> list = new ArrayList<>();
		Page<CheckList> checklist = new PageImpl<>(list);

		model.addAttribute("typeProducts", typeProducts);
		model.addAttribute("page", checklist);
		model.addAttribute("addition", "");

		return "check-list-view";
	}

	@GetMapping(value = "/create-check-list")
	public String createView(ModelMap model,
			@RequestParam(value = "page", required = false, defaultValue = Constants.PAGE_NUMBER) int page,
			@RequestParam(value = "size", required = false, defaultValue = Constants.PAGE_SIZE) int size,
			@RequestParam(value = "sort", required = false, defaultValue = Constants.LIST_SORT_DESCENDING) String sort,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "date", required = false, defaultValue = "") String date) {

		List<Employee> qcs = employeeService
				.findByPosition(EmployeePosition.Qc.toString());

		// Update by DinhDN 15-01-2021 02:03:00
		List<ProductLot> productLots = lotSevice.getAllProductLot();

		// Update by LamPQT 19-01-2021 10:24 PM
		CheckListSettingDTO checkListDefault = null;

		// Update by LamPQT 02-01-2021 03:45 PM
		List<DefaultSetting> defaultSettings = settingService
				.getDefaultByFeature("qa_check");

		Page<CheckList> checklistCurrentDay = null;
		Sort sortable = checkListSevice.setSort(sort);
		Pageable pageable = PageRequest.of(page , size, sortable);
		LocalDate dates = null;
		if ("".equals(date)) {
			dates = LocalDate.now();
		} else {
			dates = LocalDate.parse(date);
		}
		if (dates == null) {
			model.addAttribute("state", "ERR");
			model.addAttribute("dto", new CheckListDTO());

			return "check-list-new";
		}

		try {

			checklistCurrentDay = checkListSevice.findAll(dates, keyword, pageable);

			checkListDefault = settingService.convert(defaultSettings);

		} catch (Exception e) {
			checkListDefault = new CheckListSettingDTO();
			checklistCurrentDay = new PageImpl<>(new ArrayList<CheckList>());
			// log
		}
		
		
		
		model.addAttribute("dto", new CheckListDTO());
		model.addAttribute("keyword", keyword);
		model.addAttribute("date", date);
		model.addAttribute("page", checklistCurrentDay);
		return "check-list-new";
	}

	@GetMapping(path = "/approval-check-list")
	public String approveView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = Constants.PAGE_NUMBER) int page,
			@RequestParam(value = "size", required = false, defaultValue = Constants.PAGE_SIZE) int size,
			@RequestParam(value = "sort", required = false, defaultValue = Constants.LIST_SORT_DESCENDING) String sort,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {

		Page<CheckList> checklist = null;
		Sort sortable = checkListSevice.setSort(sort);
		Pageable pageable = PageRequest.of(page , size, sortable);

		checklist = checkListSevice.findAll(LocalDate.now(), keyword, pageable);
		Page<CheckList> pages = new PageImpl<CheckList>(checklist.getContent(),
				PageRequest.of(page, size, sortable), checklist.getTotalPages());

		model.addAttribute("page", pages);
		model.addAttribute("keyword", keyword);

		return "check-list-approval";
	}

	@PostMapping(path = "/create")
	public RedirectView create(@Valid @ModelAttribute("dto") CheckListDTO dto,
			BindingResult bindingResult, RedirectAttributes redirectAttr, ModelMap model,
			HttpServletRequest request) {

		Employee qc = employeeService.getEmployeeByUsername(dto.getQc());
		Optional<ProductLot> lot = lotSevice.findByLotCode(dto.getLotCode());
		String referrer = request.getHeader("referer");

		if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) { // has error
																			// set state
																			// is
																			// CREATE_FAILED
																			// and
																			// return
			LOGGER.info("Binding has error! " + bindingResult.getAllErrors());

			return new RedirectView(referrer);
		} else {

			if (qc == null && !lot.isPresent()) {
				bindingResult.addError(new FieldError("dto", "qc",
						"Không có Người giám sát này trong hệ thống"));
				bindingResult.addError(new FieldError("dto", "lotCode",
						"Không có Mã này trong hệ thống"));
				return new RedirectView(referrer);
			} else if (qc == null && lot.isPresent()) {
				bindingResult.addError(new FieldError("dto", "qc",
						"Không có Người giám sát này trong hệ thống"));
				return new RedirectView(referrer);
			} else if (qc != null && !lot.isPresent()) {
				bindingResult.addError(new FieldError("dto", "lotCode",
						"Không có Mã này trong hệ thống"));
				return new RedirectView(referrer);
			} else {
				dto.setId(0);
				CheckList checklist = CheckList.mapping(dto);
				checklist.setQc(qc);
				checklist.setOwn(qc);
				checklist.setLot(lot.get());
				checklist.setStatus(Status.Pending);

				String results = checkListSevice.createCheckList(checklist);

				redirectAttr.addFlashAttribute("state", "CREATE_" + results);
				return new RedirectView(referrer);
			}

		}

	}

	@PostMapping(path = "/update")

	public RedirectView update(@ModelAttribute CheckListDTO dto,
			BindingResult bindingResult, RedirectAttributes redirectAttr,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("state", "UPDATE_FAILED");

		} else {
			if (dto.getId() == 0) {// id=0 stop
				redirectAttr.addFlashAttribute("state", "UPDATE_" + Constants.FAILED);

				new RedirectView("/checklist/create-check-list");
			} else {// id !=0 allow update
				Employee qc = employeeService.getEmployeeByUsername(dto.getQc());
				Optional<ProductLot> lot = lotSevice.findByLotCode(dto.getLotCode());

				CheckList checklist = CheckList.mapping(dto);
				checklist.setQc(qc);
				checklist.setOwn(qc);
				checklist.setLot(lot.get());

				checklist.setStatus(Status.Pending);

				String results = checkListSevice.createCheckList(checklist);
				redirectAttr.addFlashAttribute("state", "UPDATE_" + results);
			}
		}
		String referrer = request.getHeader("referer");
		return new RedirectView(referrer);
	}

	@PostMapping(path = "/delete")
	public RedirectView delete(@RequestParam int deleteId,
			RedirectAttributes redirectAttr, HttpServletRequest request) {
		String resultDelete = checkListSevice.delete(deleteId);

		redirectAttr.addFlashAttribute("state", "DELETE_" + resultDelete);
		String referrer = request.getHeader("referer");
		return new RedirectView(referrer);

	}

	@PostMapping(path = "/filter")
	public String filter(Model model, @ModelAttribute CheckListFilterDTO dto,
			@RequestParam(value = "addition", required = false) String addition) {

		Page<CheckList> filters = null;
		int page = 0;
		try {
			page = Integer.parseInt(addition);
		} catch (Exception e) {

		}
		try {
			filters = checkListSevice.filter(dto,
					PageRequest.of(page, dto.getSize(), Sort.unsorted()));
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<TypeProduct> typeProducts = typeProductService.getAllTypeProduct();
		model.addAttribute("typeProducts", typeProducts);

		model.addAttribute("page", filters);

		model.addAttribute("dto", dto); // send data of dto to set field of filter
		model.addAttribute("addition", addition);
		return "check-list-view";
	}

	@PostMapping(path = "/report")
	public void report(Model model, @ModelAttribute CheckListFilterDTO dto,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttr

	) throws IOException {

		List<CheckList> reportChecklist = checkListSevice.report(dto);
		Locale loc = LocaleContextHolder.getLocale();
		System.out.println(reportChecklist);
		if (reportChecklist.size() == 0) {
			// list empty >> not export and show nofitication
			redirectAttr.addFlashAttribute("state", "EXPORT_EMPTY");
		} else {

			response.setContentType("application/octet-stream");
			String headerKey = "Content-Disposition";

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd__HH_mm_ss");
			String formatDateTime = now.format(formatter);

			String headerValue = "attachment; filename=checklist_" + formatDateTime
					+ ".xlsx";
			response.setHeader(headerKey, headerValue);

			redirectAttr.addFlashAttribute("state", "EXPORT_SUCCESS");
			List<TypeProduct> typeProducts = typeProductService.getAllTypeProduct();

			redirectAttr.addFlashAttribute("typeProducts", typeProducts);

			redirectAttr.addFlashAttribute("dto", dto); // send data of dto to set field
														// of filter

			checkListExportService.export(response, reportChecklist, dto, loc);
		}
	}

	@PostMapping(path = "/setting")
	public RedirectView setting(@ModelAttribute("settingObject") CheckListSettingDTO dto,
			RedirectAttributes redirectAttr) {

		try {
			settingService.updateCheckListSetting(dto);
			redirectAttr.addFlashAttribute("state", "SETTING_SUCCESS");
		} catch (Exception e) {
			redirectAttr.addFlashAttribute("state", "SETTING_FAILED");
		}

		return new RedirectView("/checklist/create-check-list");
	}

	@ModelAttribute("qcs")
	public List<Employee> getQCs() {
		return employeeService.findByPosition(EmployeePosition.Qc.toString());
	}

	@ModelAttribute("lots")
	public List<ProductLot> getProductLots() {
		return lotSevice.getAllProductLot();
	}

	@ModelAttribute("settingObject")
	public CheckListSettingDTO getDefaultSetting() {
		// Update by LamPQT 19-01-2021 10:24 PM
		CheckListSettingDTO checkListDefault = null;
		// Update by LamPQT 02-01-2021 03:45 PM
		List<DefaultSetting> defaultSettings = settingService
				.getDefaultByFeature("qa_check");

		try {

			checkListDefault = settingService.convert(defaultSettings);
			return checkListDefault;
		} catch (Exception e) {
			checkListDefault = new CheckListSettingDTO("", "", "", true, true, "", "", "",
					"", "", "", "");
			return checkListDefault;
			// log
		}

	}

	@ModelAttribute("page")
	public Page<CheckList> getPages() {
		Page<CheckList> pages = null;
		try {
			pages = checkListSevice.getCheckListCurrentDay(
					PageRequest.of(Integer.valueOf(Constants.PAGE_NUMBER),
							Integer.valueOf(Constants.PAGE_SIZE),
							checkListSevice.setSort(Constants.LIST_SORT_DESCENDING)));

		} catch (Exception e) {

			pages = new PageImpl<>(new ArrayList<CheckList>());
			// log
		}
		return pages;
	}

	@PostMapping(path = "/approval")
	public RedirectView approval(HttpServletRequest request,
			@RequestParam(value = "ids") Integer[] ids, RedirectAttributes redirectAttr) {
		String result = "";
		String mess = "";

		for (Integer id : ids) {

			result = checkListSevice.approval(id);
			if (result.equals(Constants.FAILED)) {
				mess += result + "-" + id + ";";
			}
		}

		String referrer = request.getHeader("referer");

		redirectAttr.addFlashAttribute("approvalStatus", mess);
		return new RedirectView(referrer);

	}

	@PostMapping(path = "/reject")

	public RedirectView reject(HttpServletRequest request,
			@RequestParam(value = "ids") Integer[] ids, RedirectAttributes redirectAttr) {
		String result = "";
		String mess = "";
		for (Integer id : ids) {

			result = checkListSevice.reject(id);
			if (result.equals(Constants.FAILED)) {
				mess += result + "-" + id + ";";
			}
		}
		String referrer = request.getHeader("referer");

		redirectAttr.addFlashAttribute("rejectStatus", mess);
		return new RedirectView(referrer);
	}

	@PostMapping(path = "/remove")

	public RedirectView removes(HttpServletRequest request,
			@RequestParam(value = "ids") Integer[] ids, RedirectAttributes redirectAttr) {
		String result = "";
		String mess = "";
		for (Integer id : ids) {

			result = checkListSevice.remove(id);
			if (result.equals(Constants.FAILED)) {
				mess += result + "-" + id + ";";
			}
		}
		String referrer = request.getHeader("referer");

		redirectAttr.addFlashAttribute("removeStatus", mess);
		return new RedirectView(referrer);
	}
}
