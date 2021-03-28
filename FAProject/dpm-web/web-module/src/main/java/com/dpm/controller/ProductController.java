package com.dpm.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dpm.constant.Constants;
import com.dpm.dao.ProductDAO;
import com.dpm.dto.ProductDTO;
import com.dpm.dto.ProductDTO1;
//import com.dpm.dto.ProductDTO;
import com.dpm.dto.ProductReportDTO;
import com.dpm.entity.DefaultSetting;
import com.dpm.entity.Machine;
import com.dpm.entity.Product;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.FinishedProductReportService;
import com.dpm.service.MachineService;
import com.dpm.service.ProductService;
import com.dpm.service.TypeProductService;

@Controller
@RequestMapping({ "/product" })
public class ProductController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private FinishedProductReportService finishedProductReportService;

	@Autowired
	private TypeProductService typeProductService;

	@Autowired
	private MachineService machineService;
	
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	private DefaultSettingService settingService;

	@Autowired
	DefaultSettingService defaultSettingService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	/**
	 * Function send list type products to VIEW 
	 * @return
	 */
	@ModelAttribute("listTypeProducts")
	public List<TypeProduct> getTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	/**
	 * Function send list machines to VIEW 
	 * @return
	 */
	@ModelAttribute("listMachines")
	public List<Machine> getListMachine() {
		return machineService.findAllMachine();
	}

	/**
	 * Function get default setting
	 * @return
	 */
	@ModelAttribute("defaultFinishedProductReport")
	public ProductDTO1 getDefaultFinishedProductReport() {
		List<DefaultSetting> defaultSettings = settingService.getDefaultByFeature("fn_pd_report");
		ProductDTO1 productDTO1 = new ProductDTO1();
		productDTO1 = settingService.convertToProductDTO1(defaultSettings);
		return productDTO1;
	}
	
	/**
	 * Function set default setting
	 * @return
	 */
	@GetMapping(value = "/set-default-value")
	public String setDefaultValue(@ModelAttribute("defaultFinishedProductReport") ProductDTO1 productDTO1, Model model) {
		
		defaultSettingService.updateFinishedProductReportSetting(productDTO1);
			
		return "redirect:/product/new";

	}

	/**
	 * Function get all product and send to report-product.html
	 * @param model
	 * @param pageNum
	 * @param size
	 * @param sort
	 * @return report-product.html page
	 */
	@GetMapping(value = { "", "/" })
	public ModelAndView index(ModelMap model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {

		Sort sortable = null;
		Page<Product> list = null;
		try {
			if (sort.equals("DESC")) {
				sortable = Sort.by("modifyDate").descending();
			} else {
				sortable = Sort.by("modifyDate").ascending();
			}

			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			list = productService.listAllPage(pageable);

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.addAttribute("listProducts", list);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", size);

		return new ModelAndView("report-product", model);
	}
	
	/**
	 * Submit get-product
	 * @param rdto
	 * @param model
	 * @param pageNum
	 * @param size
	 * @param sort
	 * @return report-product.html page
	 */
	@GetMapping(value = "/get-report")
	public String getReport(@ModelAttribute ProductReportDTO rdto, Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {

		Sort sortable = null;
		Page<Product> list = null;
		try {
			if (sort.equals("DESC")) {
				sortable = Sort.by("productName").descending();
			} else {
				sortable = Sort.by("productName").ascending();
			}

			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			list = productService.getReport(rdto, pageable);

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.addAttribute("listProducts", list);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", size);
		model.addAttribute("typeProductId", rdto.getTypeProductId());
		model.addAttribute("machineId", rdto.getMachineId());
		model.addAttribute("fromDate", rdto.getFromDate());
		model.addAttribute("toDate", rdto.getToDate());

		return "report-product";

	}

	/**
	 * Function get product from new-product.html
	 * @param model
	 * @return new-product.html
	 */
	@RequestMapping("/new")
	public String showNewProductPage(Model model) {
		ProductDTO1 productDTO1 = new ProductDTO1();
		model.addAttribute("produc1t", productDTO1);

		return "new-product";
	}
	
	/**
	 * Function send product to VIEW from updating
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id,
			Model model) {
		ModelAndView mav = new ModelAndView("edit-product");

		Product product = productService.get(id);
		ProductDTO1 productDTO1 = new ProductDTO1();
		productDTO1 = product.toDTO(product);
		mav.addObject("produc1t", productDTO1);

		return mav;
	}
	
	/**
	 * Function save product for create new or update
	 * @param product
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("produc1t") ProductDTO1 productDTO1,
			RedirectAttributes redirectAttributes
			) {
		if(!productDao.existsByProductId(productDTO1.getProductId())) {
			if(productService.insert(productDTO1)) {
				redirectAttributes.addFlashAttribute("state", "CREATE_SUCCESS");
			} else {
				redirectAttributes.addFlashAttribute("state", "CREATE_FAIL");
			}
		} else {
			if(productService.update(productDTO1)) {
				redirectAttributes.addFlashAttribute("state", "UPDATE_SUCCESS");
			} else {
				redirectAttributes.addFlashAttribute("state", "UPDATE_FAIL");
			}
		}
		return "redirect:/product/all";
	}

	/**
	 * Function update product
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/delete/{deleteId}")
	public String deleteProduct(@PathVariable(name = "deleteId") int id,
			RedirectAttributes redirectAttributes
			) {
		
		if(productService.deleteProductById(id)) {
			redirectAttributes.addFlashAttribute("state", "DELETE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "DELETE_FAILED");
		}
		return "redirect:/product/all";
	}
	
	/**
	 * Function export excel
	 * @param response
	 * @param rdto
	 * @throws IOException
	 */
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response,
			@ModelAttribute ProductReportDTO rdto) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		Sort sortable = null;
		Page<Product> listExportPaging = null;
		List<Product> listExport = new ArrayList<Product>();
		String sort = "";
		try {
			if (sort.equals("DESC")) {
				sortable = Sort.by("productName").descending();
			} else {
				sortable = Sort.by("productName").ascending();
			}

			List<Product> listAllProduct = productService.listAll();

			Pageable pageable = PageRequest.of(0, listAllProduct.size(), sortable);

			listExportPaging = productService.getReport(rdto, pageable);

			listExport = listExportPaging.getContent();

		} catch (Exception e) {

			e.printStackTrace();
		}

		ProductExcelExporter excelExporter = new ProductExcelExporter(listExport, rdto);

		excelExporter.export(response);
	}

	/**
	 * Function Search
	 * @param model
	 * @param pageNum
	 * @param size
	 * @param sort
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = { "/all" }, method = RequestMethod.GET)
	public String getAllWithSearchAndPagination(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {

		Sort sortable = null;
		Page<Product> list = null;
		try {
			if (sort.equals("DESC")) {
				sortable = Sort.by("modifyDate").descending();
			} else {
				sortable = Sort.by("modifyDate").ascending();
			}

			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			if (keyword == null) {
				list = productService.getListProductAndPaginationWithSort(pageable);

			} else {
				list = productService.searchByCodeOrNameAndPagination(pageable, keyword);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.addAttribute("listProducts", list);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", size);
		model.addAttribute("keyword", keyword);

		model.addAttribute("product", new Product());

		return "product";
	}
	
	/**
	 * Send pending product to product-approve.html
	 * @param model
	 * @param pageNum
	 * @param size
	 * @param sort
	 * @return
	 */
	@GetMapping(path = "/product-approve")
	public ModelAndView approveRecord(ModelMap model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,

			
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort
			) {

		Sort sortable = null;
		Page<Product> list = null;
		try {
			if (sort.equals("DESC")) {
				sortable = Sort.by("product_id").descending();
			} else {
				sortable = Sort.by("product_id").ascending();
			}

			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			list = productDao.getPending(pageable);

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.addAttribute("listProducts", list);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("pageSize", size);
		model.addAttribute("product", new Product());
		
		return new ModelAndView ("product-approve", model);
	}
	
	/**
	 * Function approve product
	 * @param idList
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/approve-selected")
	public String approveSelected(@RequestParam List<Integer> idList,
			RedirectAttributes redirectAttributes
			) {
		LOGGER.info(idList.toString());
		String approveResult = productService.approve(idList);
		if (approveResult.equals(Constants.SUCCESS)) {
			redirectAttributes.addFlashAttribute("state", "APPROVE_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "APPROVE_FAILED");
		}
		return "redirect:/product/all";
	}
	
	/**
	 * Function reject product
	 * @param idList
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/reject-selected")
	public String rejectSelected(@RequestParam List<Integer> idList,
			RedirectAttributes redirectAttributes
			) {
		LOGGER.info(idList.toString());
		String rejectResult = productService.reject(idList);
		if (rejectResult.equals(Constants.SUCCESS)) {
			redirectAttributes.addFlashAttribute("state", "REJECT_SUCCESS");
		} else {
			redirectAttributes.addFlashAttribute("state", "REJECT_FAILED");
		}
		return "redirect:/product/all";
	}
	
	@PostMapping(value = "/approve")
	public String approve(@RequestParam int id) {
		String approveResult = productService.approve(id);
		return "redirect:/product/all";
	}
	
	@PostMapping(value = "/reject")
	public String reject(@RequestParam int id) {
		String rejectResult = productService.reject(id);
		return "redirect:/product/all";
	}
	
	@PostMapping(value = "/delete-selected")
	public String deleteSelected(@RequestParam List<Integer> idList) {
		String rejectResult = productService.deleteSelected(idList);
		return "redirect:/product/all";
	}
	
}
