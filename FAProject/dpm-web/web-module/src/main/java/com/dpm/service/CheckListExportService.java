package com.dpm.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import com.dpm.dto.CheckListFilterDTO;
import com.dpm.entity.CheckList;

public interface CheckListExportService {

	
	public void export(HttpServletResponse response,List<CheckList> checkLists, CheckListFilterDTO filter, Locale loc ) throws IOException;
}
