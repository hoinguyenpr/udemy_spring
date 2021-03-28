package com.dpm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.ScaleCalibration;

/**
 * 
 * @author ThuanLV6- 2/2/2020
 *
 */
public interface ScaleCalibrationCustomDao {

	public Page<ScaleCalibration> filter(ScaleCalibrationFilterDto filter, Pageable pageable);
	
	public List<ScaleCalibration> export(ScaleCalibrationFilterDto filter);
}
