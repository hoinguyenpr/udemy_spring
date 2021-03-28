package com.dpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpm.constant.Constants;
import com.dpm.entity.Additive;
import com.dpm.service.AdditiveService;

@Controller
@RequestMapping("/additive")
public class AdditiveController {

	@Autowired
	private AdditiveService additiveService;

	/**
	 * @author DuongNM: Default url view list
	 *
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(Model model) {

		// Get sort object default
		Sort sort = additiveService.setSort("ASC");

		// Get list all additives from DB with default
		List<Additive> listAdditives = additiveService.getListAdditiveAndPaginationWithSort(10, 0, sort);

		// Attributes
		model.addAttribute("listAdditives", listAdditives);
		model.addAttribute("additive", new Additive());
		model.addAttribute("pageNumber", 1);
		model.addAttribute("pageSize", 10);
		model.addAttribute("pageTotal", additiveService.getTotalPage(10));
		model.addAttribute("keyword", "");

		// Return view
		return "additive";
	}

	/**
	 * @author DuongNM: Save to DB
	 *
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String postEditOrCreateAdditive(@RequestBody Additive additive) {

		// Edit or create new additive
		if (additiveService.editOrCreateAdditive(additive).equals(Constants.SUCCESS)) {
			return Constants.SUCCESS;
		} else {
			return Constants.FAILED;
		}
	}

	/**
	 * @author DuongNM: Delete row in DB
	 *
	 */
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public String postDeleteAdditive(@RequestParam("deleteId") String id) {
//		Integer deleteId = Integer.parseInt(id);
//
//		// Delete additive by id
//		additiveService.deleteAdditiveById(deleteId);
//
//		// Return view
//		return "redirect:/additive/all";
//	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String postDeleteAdditive(@RequestBody Additive additive) {

		// Delete additive by id
		if(additiveService.deleteAdditiveById(additive.getId()).equals(Constants.SUCCESS)) {
			return Constants.SUCCESS;
		} else {
			return Constants.FAILED;
		}
	}

	/**
	 * @author DuongNM: View additives with pagination
	 *
	 */
	@RequestMapping(value = { "/all" }, method = RequestMethod.POST)
	public String getAllWithSearchAndPagination(Model model,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSort", required = false, defaultValue = "ASC") String pageSort) {

		// Get sort object default
		Sort sort = additiveService.setSort(pageSort);

		// Get list all additives from DB
		List<Additive> listAdditives = additiveService.getListAdditiveAndPaginationWithSort(pageSize, pageNumber - 1,
				sort);

		// Attributes
		model.addAttribute("listAdditives", listAdditives);
		model.addAttribute("additive", new Additive());
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageTotal", additiveService.getTotalPage(pageSize));
		model.addAttribute("keyword", "");
		// Return view
		return "additive";
	}

	/**
	 * @author DuongNM: Choose page number by user with pagination
	 *
	 */
	@RequestMapping(value = { "/all/{pageSize}/{pageNumber}" }, method = RequestMethod.GET)
	public String getAllWithSearchAndPaginationChoosePageNumber(Model model,
			@PathVariable(name = "pageSize") Integer pageSize, 
			@PathVariable(name = "pageNumber") Integer pageNumber,
			@RequestParam(name = "pageSort", required = false, defaultValue = "ASC") String pageSort,
			@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {

		// Get sort object default
		Sort sort = additiveService.setSort(pageSort);
		List<Additive> listAdditives = null;
		if (keyword.equals("")) {
			// Get list all additives from DB
			listAdditives = additiveService.getListAdditiveAndPaginationWithSort(pageSize, pageNumber - 1, sort);
		} else {
			listAdditives = additiveService.searchByCodeOrNameAndPagination(pageSize, pageNumber - 1, sort, keyword);
		}

		// Attributes
		model.addAttribute("keyword", keyword);
		model.addAttribute("listAdditives", listAdditives);
		model.addAttribute("additive", new Additive());
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageTotal", additiveService.getTotalPage(pageSize, keyword));
		// Return view
		return "additive";
	}
}
