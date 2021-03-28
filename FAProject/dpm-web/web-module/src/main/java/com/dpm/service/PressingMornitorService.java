package com.dpm.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.PressingMonitorDTO;
import com.dpm.entity.PressingMornitor;

public interface PressingMornitorService {

	Page<PressingMornitor> listAll(Pageable pageable);
	
	Page<PressingMornitor> listAllByStatus(Integer status, Pageable pageable);

	Optional<PressingMornitor> getById(Integer id);
	
	Page<PressingMornitor> findAllByDay(LocalDate date, Pageable pageable);
	
	Page<PressingMornitor> findByInputTimeBetween(LocalDate start, LocalDate end, Pageable pageable);
	
	Page<PressingMornitor> findAllByStatusAndInputTimeBetween(Integer status, LocalDate start, LocalDate end, Pageable pageable);

	boolean insert(PressingMonitorDTO pm);

	boolean update(PressingMonitorDTO pm);

	boolean delete(Integer id);
	
	boolean refuse(Integer[] ids);
	
	boolean approve(Integer[] ids);

	File exportExcelByDate(LocalDate startDate, LocalDate endDate, Integer status) throws IOException;
	
	PressingMornitor getDefaultValue();
	
	boolean setDefaultValueByEntity(PressingMornitor pm);
	
	boolean setDefaultValueById(Integer id);
	
}
