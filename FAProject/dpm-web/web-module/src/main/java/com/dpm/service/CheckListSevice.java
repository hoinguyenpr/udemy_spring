package com.dpm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dpm.dto.CheckListFilterDTO;
import com.dpm.entity.CheckList;
import com.dpm.utility.Status;

public interface CheckListSevice {

	public String createCheckList(CheckList checkList);

	public Page<CheckList> getCheckListCurrentDay(Pageable pageable);

	public String delete(int deleteId);

	public Optional<CheckList> get(int id);

//	public Page<CheckList> filter(String shift, String date, int lot, Pageable pageable);
//
//	public Page<CheckList> filter(String shift, String date, String typeProduct, Pageable pageable);
//	
//	public Page<CheckList> filter(String shift, String date, Pageable pageable);

	public Sort setSort(String sortBy);

	public CheckList getByStatus(Status default1);
	
//	public List<CheckList> getReportCheckList(String shift, String date, int lot);
	
	public List<CheckList> report(CheckListFilterDTO filter);
	
	public Page<CheckList> filter(CheckListFilterDTO filter, Pageable pageable);

	public String setting(int settingId);
	
	public Page<CheckList> findAll(LocalDate date, String keyword, Pageable pageable);
	
	public String approval(int approveId);
	
	public String reject(int rejectId);

	public String remove(Integer id);
}
