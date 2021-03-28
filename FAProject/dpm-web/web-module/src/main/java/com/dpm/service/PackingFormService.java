package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.PackingFormDTO;
import com.dpm.entity.MetalDetector;
import com.dpm.entity.PackingForm;
import com.google.common.util.concurrent.ExecutionError;


/**
 * Author:  HoiNX1        Modified date: 14/01/2020 11:37 AM     
 * Modifier: 
 */

public interface PackingFormService {
	/**
	 * Function get all MetalDetector from database.
	 * 
	 * @param pageable
	 * @return List object MetalDetector
	 */
	public PackingForm save(PackingForm packingForm);
	
	public List<PackingForm> getAllPackingForm();
	
	public Page<PackingForm> getAllPackingForm(Pageable pageable);

	public Page<PackingForm> searchPackingForm(Pageable pageable, String keyword);

	public Boolean deletePackingFormByID(Integer packingFormId);

	public PackingForm  getPackingFormById(Integer packingFormId);
	
	public Boolean saveOrUpdate(PackingFormDTO packingFormDTO) throws Exception;
}
