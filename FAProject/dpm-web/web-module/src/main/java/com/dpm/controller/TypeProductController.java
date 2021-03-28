package com.dpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpm.entity.TypeProduct;
import com.dpm.service.TypeProductService;

@Controller
@RequestMapping("/TypeProduct")
public class TypeProductController {

	@Autowired
	private TypeProductService typeProductService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

//	static List<String> typeproduct = null;
//	static {
//		typeproduct = new ArrayList<String>();
//		typeproduct.add("BSD");
//		typeproduct.add("BSD");
//		typeproduct.add("BSD");
//		typeproduct.add("BSD");
//		typeproduct.add("BSD");
//		typeproduct.add("BSD");
//	}

//	@GetMapping("/list")
//	public String getAllMaLoaiSanPham(ModelMap model)
//	{
//		String key = "BSD8";
//		List<TypeProduct> typeproducts = typeProductService.listAll(key);
//		//List<TypeProduct> typeproducts = typeProductService.getAllTypeProduct();
//		model.addAttribute("typeproducts", typeproducts);
//		return "typeproduct";
//
//	}

	@GetMapping(value = { "/", "search", "maloaisanpham" })
	public String getAllMaLoaiSanPham(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@Param("search") String search) {

		Sort sortable = null;

		Page<TypeProduct> list = null;
		try {
			if (sort.equals("ASC")) {
				sortable = Sort.by("id").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("id").descending();
			}
			Pageable pageable = PageRequest.of(pageNum, size, sortable);

			if (search != null) {
				list = typeProductService.listAllForSearch(pageable, search);

			} else {
				list = typeProductService.getListTypeProductCodePaging(pageable);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
//		model.addAttribute("typeproduct",typeproduct);
		model.addAttribute("typeproduct", new TypeProduct());
		model.addAttribute("typeproducts", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("totalItems", list.getTotalElements());
		model.addAttribute("pageSize", size);
		// model.addAttribute("productAdd", new TypeProduct());

		return "typeproduct";

	}

//	@GetMapping("/delete/{id}")
//	public String deleteMaLoaiSanPham( @PathVariable(required= false, value="id") Integer id) {
//
//		try {
//			typeProductService.deleteByTypeProductCode(id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return "redirect:/TypeProduct/";
//
//	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteMaLoaiSanPham(@RequestParam("deleteId") Integer id) {
		try {
			System.out.println("\n\n\n id: " + id);
			typeProductService.deleteByTypeProductCode(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/TypeProduct/";
	}

	@GetMapping(path = "/getAccountById/{maLoaiSanPham}")
	@ResponseBody
	public TypeProduct getTypeProductById(
			@PathVariable("maLoaiSanPham") String maLoaiSanPham) {
		TypeProduct typeProduct = null;

		try {

			typeProduct = typeProductService
					.getTypeProductByTypeProductCode(maLoaiSanPham);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return typeProduct;

	}

	//modify by DinhDN
	@PostMapping("/save")
	public String createAndUpdate(
			@ModelAttribute("typeproduct") TypeProduct typeProduct) {

		typeProductService.saveOrUpdate(typeProduct);

		return "redirect:/TypeProduct/";

	}

}
