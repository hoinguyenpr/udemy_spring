package com.dpm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.CheckListFilterDTO;
import com.dpm.entity.CheckList;

public interface CheckListCustomDAO  {
	
	
	public List<CheckList> report(CheckListFilterDTO filter);
	
	public Page<CheckList> filter(CheckListFilterDTO filter, Pageable pageable);
}
