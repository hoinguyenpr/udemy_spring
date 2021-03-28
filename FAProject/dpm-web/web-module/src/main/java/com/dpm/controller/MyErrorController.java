package com.dpm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyErrorController implements ErrorController{

	@GetMapping(value = "/error")
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

		ModelAndView errorPage = new ModelAndView();
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
			case 400: {
				errorPage.setViewName("error-pages/error-400");
				break;
			}
			case 403: {
				errorPage.setViewName("error-pages/error-403");
				break;
			}
			case 404: {
				errorPage.setViewName("error-pages/error-404");
				break;
			}
			case 405: {
				errorPage.setViewName("error-pages/error-405");
				break;
			}
			case 500: {
				errorPage.setViewName("error-pages/error-500");
				break;
			}
			case 505: {
				errorPage.setViewName("error-pages/error-505");
				break;
			}
			default: {
				errorPage.setViewName("error-pages/error-500");
				break;
			}
		}
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
