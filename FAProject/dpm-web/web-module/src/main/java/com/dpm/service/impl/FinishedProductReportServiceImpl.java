package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.FinishedProductReportDAO;
import com.dpm.dao.ProductDAO;
import com.dpm.entity.Employee;
import com.dpm.entity.FinishedProductReport;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.Product;
import com.dpm.service.FinishedProductReportService;
import com.dpm.service.ProductService;

@Service
public class FinishedProductReportServiceImpl implements FinishedProductReportService {
	
	@Autowired
	FinishedProductReportDAO finishedProductReportDAO;

	@Override
	public FinishedProductReport save(FinishedProductReport finishedProductReport) {
		
		return finishedProductReportDAO.save(finishedProductReport);
	}
	
}
