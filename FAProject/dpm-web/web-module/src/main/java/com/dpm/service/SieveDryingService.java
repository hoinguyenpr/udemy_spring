package com.dpm.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.SieveDryingDTO;
import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.SieveDrying;

public interface SieveDryingService {

	public String updateSieveDrying(SieveDryingDTO newSieveDrying);

	public String deleteSieveDrying(int id);

	public SieveDrying getSieveDryingById(Integer id);
	
	public List<SieveDrying> getAll();

	public Page<SieveDrying> ListAllPaging(Pageable pageable);

	public Page<SieveDrying> listAllForSearch(Pageable pageable, String search);

	public String createStr(SieveDrying sieveDrying);
	
	public List<SieveDrying> getAllAvailable();

	public Page<SieveDrying> getAllAvailable(Pageable pageable);

	public SieveDrying getDefault();
	
	public String setDefault(SieveDryingDTO defaultSieveDrying);
	
	public Page<SieveDrying> getReport(SieveDryingReportDTO rdto, Pageable pageable);

	public List<SieveDrying> getAllButNotDefault();
	
	public Page<SieveDrying> getAllButNotDefault(Pageable pageable);
	
	public List<SieveDrying> getPending();

	public String approveSieveDrying(int id);
	
	public String rejectSieveDrying(int id);
	
	void exportReport(HttpServletResponse response, SieveDryingReportDTO rdto);
	
	public boolean isValid(SieveDryingDTO dto);
	
	public boolean isValid(SieveDrying sd);

	public String approveSieveDrying(List<Integer> idList);
	
	public String rejectSieveDrying(List<Integer> idList);
}
