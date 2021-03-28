package com.dpm.service;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import com.dpm.entity.TrackingPHBx;

public interface TrackingPHBxFormService {

	public List<TrackingPHBx> listForms();

	public void addForm(TrackingPHBx form);

	public TrackingPHBx findFormById(int id);
	
	public boolean deleteById(int id);
	
	public Page<TrackingPHBx> listFormsByDate(LocalDate date, int page);
	
	public List<TrackingPHBx> listFormsByDate(LocalDate date);
	
	public void exportExcel(HttpServletResponse response, List<TrackingPHBx> exportList);
	
	public Page<TrackingPHBx> listFormsByStatus(LocalDate date, int status, int page);
	
	public void approvalSelected(Integer[] list, int status);
}
