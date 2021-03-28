package com.dpm.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.ScaleCalibration;

/**
 * @author ThuanLV6
 * created at 01/12/2021
 */
public interface ScaleCalibrationService {

	
	public ScaleCalibration saveScaleCalibration(ScaleCalibration scaleCalibration);
	
	public void updateScaleCalibration(ScaleCalibration scaleCalibration);
	
	public void deleteScaleCalibration(int id);
	
	public ScaleCalibration findScaleCalibrationById(int id);
	
	public List<ScaleCalibration> findAll();
	
	public ScaleCalibration add(ScaleCalibration scaleCalibration);
	
	public ScaleCalibration deleteByStatus(int id);
	
	public void saveAll(List<ScaleCalibration> scaleCalibrationList);
	
	
	// pagination
	public Page<ScaleCalibration> getPage(Pageable pageable);
	
	// pagination with search and filter
	public Page<ScaleCalibration> getPageWithSearchAndFilter(ScaleCalibrationFilterDto filterDto, Pageable pageable);
	
	// export data
	public List<ScaleCalibration> getReportWithSearchAndFilter(ScaleCalibrationFilterDto filterDto);

}
