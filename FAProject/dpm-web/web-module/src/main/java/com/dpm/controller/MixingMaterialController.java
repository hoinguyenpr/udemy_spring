/**
 * 
 */
package com.dpm.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpm.dto.FilterMixingDTO;
import com.dpm.dto.MixingMaterialDTO;
import com.dpm.entity.Additive;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;
import com.dpm.service.AdditiveService;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.MixingMaterialService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeProductService;

/**
 * @author NguyenND6 created 14/01/2021
 *
 */
@Controller
@RequestMapping("/mixing")
public class MixingMaterialController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetalDetectorController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	public String finalString = null;

	@Autowired
	private MixingMaterialService mixingMaterialService;

	@Autowired
	private TypeProductService typeProductService;

	@Autowired
	private MachineService machineService;

	@Autowired
	private IngredientBatchService ingredientBatchService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductLotSevice productService;

	@Autowired
	private AdditiveService addtitiveSerice;

	@Autowired
	private DefaultSettingService defaultSettingService;

	@ModelAttribute("machineList")
	public List<Machine> getListMachine() {
		return machineService.getAll();
	}

	@ModelAttribute("tpList")
	public List<TypeProduct> getListTypeProduct() {
		return typeProductService.getAllTypeProduct();
	}

	@ModelAttribute("emList")
	public List<Employee> getListEmployee() {
		return employeeService.getAllEmployee();
	}

	@ModelAttribute("proLotList")
	public List<ProductLot> getListProductLot() {
		return productService.getAllProductLot();
	}

	@ModelAttribute("addList")
	public List<Additive> getListAdditive() {
		return addtitiveSerice.getAll();
	}

	@ModelAttribute("ingBaList")
	public List<IngredientBatch> getListIngredient() {
		return ingredientBatchService.getAll();
	}

	@GetMapping("/create")
	public String showNewMixing(Model model) {
		MixingMaterialDTO mixingMaterialDTO = new MixingMaterialDTO();

		mixingMaterialDTO = defaultSettingService.getMixingMaterialDefault();

		model.addAttribute("add", true);
		model.addAttribute("mixing", mixingMaterialDTO);
		
		return "new-mixing";
	}

	@PostMapping(path = "/save")
	public String saveMixing(@ModelAttribute("mixing") @Valid MixingMaterialDTO mixingMaterialDTO,
			BindingResult bindingResult, Model model) {
		System.out.println(mixingMaterialDTO.toString());
		if (bindingResult.hasErrors()) {

			model.addAttribute("add", true);
			return "new-mixing";
		} else {
			mixingMaterialService.createNewMixing(mixingMaterialDTO);

			return "redirect:/mixing/list";
		}
	}

	@GetMapping(value = { "/{id}/update" })
	public String updateMixing(Model model, @PathVariable int id) {

		MixingMaterialDTO mixingMaterialDTO = mixingMaterialService.getMixingDTOById(id).get();

		model.addAttribute("add", false);
		model.addAttribute("mixing", mixingMaterialDTO);
		return "new-mixing";
	}

	@PostMapping(value = { "/update" })
	public String updateMixing(@ModelAttribute("mixing") @Valid MixingMaterialDTO mixingMaterialDTO,
			BindingResult bindingResult, Model model) {
		System.out.println(mixingMaterialDTO.toString());
		if (bindingResult.hasErrors()) {

			model.addAttribute("add", false);
			return "new-mixing";
		} else {
			mixingMaterialService.updateMixing(mixingMaterialDTO);

			return "redirect:/mixing/list";
		}
	}

	@GetMapping(value = { "/{id}/delete" })
	public String deleteMixing(@PathVariable int id) {
		mixingMaterialService.deleteMixingById(id);
		return "redirect:/mixing/list";

	}

	@GetMapping(value = "/approve")
	public String approve(@RequestParam(value = "id") int id) {
		mixingMaterialService.approve(id);
		return "redirect:/mixing/list";
	}

	@GetMapping(value = { "/list" })
	public String listMixing(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

		Page<MixingMaterialDTO> mixPage = mixingMaterialService.findPaginated(PageRequest.of(page, size));
		model.addAttribute("mixPage", mixPage);

		int totalPages = mixPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("filter", new FilterMixingDTO());
		return "mixing-list";
	}

	@GetMapping(value = "/search")
	public String search(Model model, @RequestParam(value = "name") String name,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size)

	{
		Page<MixingMaterialDTO> mixPage = mixingMaterialService.searchMixing(PageRequest.of(page, size), name);
		model.addAttribute("mixPage", mixPage);

		int totalPages = mixPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("filter", new FilterMixingDTO());
		return "mixing-list";
	}

	@PostMapping(value = "/filter")

	public String filter(Model model, @ModelAttribute("filter") FilterMixingDTO filterDTO,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
		Page<MixingMaterialDTO> mixPage = mixingMaterialService.filterMixing(PageRequest.of(page, size),
				filterDTO.getType(), filterDTO.getDate(), filterDTO.getMachine());
		model.addAttribute("mixPage", mixPage);

		int totalPages = mixPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
//		model.addAttribute("filter", filterDTO);
		return "mixing-list";
	}

	@PostMapping(value = "/export")
	public String export(@ModelAttribute("filter") FilterMixingDTO filterDTO, HttpServletResponse response) {

		try {
			response.setContentType("application/octet-stream");

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=mixing_material_report.xlsx";
			response.setHeader(headerKey, headerValue);

			mixingMaterialService.exportMixing(response, filterDTO.getType(), filterDTO.getDate(),
					filterDTO.getMachine());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "mixing-list";
	}
	
	@GetMapping(path="/setting")
	public void getDefaultSetting(Model model) {
		MixingMaterialDTO mixingMaterialDTO = new MixingMaterialDTO();

		mixingMaterialDTO = defaultSettingService.getMixingMaterialDefault();

	
		model.addAttribute("mixing", mixingMaterialDTO);
		
	}

	@PostMapping(path = "/setting")
	public String setDefaultSetting(@ModelAttribute("mixing") @Valid MixingMaterialDTO mixingMaterialDTO,
			BindingResult bindingResult, Model model) {
		LOGGER.info("Function getDefaultSetting of class MixingMaterial.");

		
		try {
			defaultSettingService.setMixingMaterialDefualt(mixingMaterialDTO);
			
		} catch (Exception e) {
			LOGGER.error("ERROR while getDefaultSetting: " + e.getMessage());
		}
		return "redirect:/mixing/create";

	}

}
