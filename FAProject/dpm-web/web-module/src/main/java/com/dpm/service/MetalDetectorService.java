
/**
 * 
 */
package com.dpm.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.ObjectError;

import com.dpm.dto.MetalDetectorDTO;
import com.dpm.dto.MetalDetectorFilterDTO;
import com.dpm.entity.MetalDetector;

/**
 * @author DinhDN 13/01/2021
 *
 */

public interface MetalDetectorService {

	/**
	 * Function get all MetalDetector from database.
	 * 
	 * @param pageable
	 * @return List object MetalDetector
	 */
	public Page<MetalDetector> getAllMetalDetector(Pageable pageable);

	/**
	 * Function get all MetalDetector sort by status
	 * 
	 * @param pageable
	 * @return List object MetalDetector
	 */
	public Page<MetalDetector> getAllMetalDetectorSortByStatus(Pageable pageable);

	/**
	 * Function delete MetalDetector by metalDetectorId
	 * 
	 * @param metalDetectorId
	 * @return Boolean. True if delete success, otherwise fail
	 */
	public Boolean deleteMetalDetectorById(Integer metalDetectorId);

	/**
	 * Function get MetalDetector by metalDetectorId
	 * 
	 * @param metalDetectorId
	 * @return Object MetalDetector
	 */
	public MetalDetector getMetalDetectorById(Integer metalDetectorId);

	/**
	 * Function create or update new MetalDetector in database by MetalDetectorDTO.
	 * 
	 * @param MetalDetectorDTO
	 * @return True if create or update success, otherwise false.
	 */
	public Boolean saveOrUpdate(@Valid MetalDetectorDTO metalDetectorDTO) throws Exception;

	/**
	 * Function create or update new MetalDetector in database by MetalDetector.
	 * 
	 * @param metalDetector
	 * @return True if create or update success, otherwise false.
	 */
	public Boolean saveOrUpdate(MetalDetector metalDetector);

	/**
	 * Function find data by user filter
	 * 
	 * @param metalDetectorFilterDTO
	 * @param pageable
	 * @return List object MetalDetector
	 */
	public Page<MetalDetector> filterAllByParameter(MetalDetectorFilterDTO metalDetectorFilterDTO, Pageable pageable);

	/**
	 * Function get list metal detector with filter and export to excell file.
	 * 
	 * @param response
	 * @param metalDetectorFilterDTO
	 * @return Boolean.
	 */
	public Boolean exportExcel(HttpServletResponse response, @Valid MetalDetectorFilterDTO metalDetectorFilterDTO);

	/**
	 * Function validate dateTime in form report
	 * 
	 * @param objectErrors
	 * @param date
	 * @param fieldName
	 * @return List ObjectError
	 */
	public List<ObjectError> validateDateTime(List<ObjectError> objectErrors, String date, String fieldName);

	/**
	 * Function validate typeProductCode in form report
	 * 
	 * @param objectErrors
	 * @param typeProducts
	 * @param param
	 * @param fieldName
	 * @return List ObjectError
	 */
	public List<ObjectError> validateOject(List<ObjectError> objectErrors, List<Object> Objects, String param,
			String fieldName);

	/**
	 * Function set object MetalDetectorDTO attribute from object metalDetector
	 * 
	 * @param metalDetector
	 * @param metalDetectorDTO
	 * @return object MetalDetectorDTO
	 */
	public MetalDetectorDTO setAttributeMetalDetectorDTO(MetalDetector metalDetector,
			MetalDetectorDTO metalDetectorDTO);

	/**
	 * Function search MetalDetector from database with parameter.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return object MetalDetector
	 */
	public Page<MetalDetector> searchMetalDetector(Date startDate, Date endDate, Pageable pageable);

	/**
	 * Function search MetalDetector from database with parameter.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param search
	 * @param pageable
	 * @return object MetalDetect
	 */
	public Page<MetalDetector> searchMetalDetector(Date startDate, Date endDate, String search, Pageable pageable);
}
