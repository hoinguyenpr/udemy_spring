package com.dpm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping(path = "/")
	public String home() {
		return "home";
	}
	
	@GetMapping(path = "/setting")
	public ModelAndView settingPage(ModelMap model) {
		return new ModelAndView("setting", model);
	}
	
	
	/* update DinhDN 
	
	@GetMapping(path = "/may-do-kim-loai")
	public String Maydokimloai() {
		return "/fa-morning/may-do-kim-loai";
	}
	
	*/
	
	@GetMapping(path = "/san-pham")
	public String San_Pham() {
		return "/fa-morning/san-pham";
	}
	// Thuan got scale calibration controller out of home controller
//	@GetMapping(path = "/can")
//	public String Scale_Calibration_Manage() {
//		return "/fa-morning/can";
//	}

	@GetMapping(path = "/thanh-pham-san-xuat")
	public String thanh_pham_san_xuat() {
		return "/fa-morning/thanh-pham-san-xuat";
	}
	@GetMapping(path = "/nhap-say-sang")
	public String nhapsaysang() {
		return "sieve-drying-input";
	}


	// Add by LongVT7 18/01/2021 21:51
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	
}
