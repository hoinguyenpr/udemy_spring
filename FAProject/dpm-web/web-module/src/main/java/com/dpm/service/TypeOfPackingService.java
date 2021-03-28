package com.dpm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.TypeOfPacking;;


/**
 * Author:  HoiNX1        Modified date: 14/01/2021 11:37 AM     
 * Modifier: 
 */

public interface TypeOfPackingService {
	
	public List<TypeOfPacking> saveAllRecord(List<TypeOfPacking> list);
	
	public List<TypeOfPacking> getAllTypePacking();

	public TypeOfPacking getTypeProductByTypeProductCode(String typePackingCode)
			throws Exception;

	public TypeOfPacking getTypeProductByTypeProductName(String typePackingName)
			throws Exception;

	public boolean createOrUpdate(TypeOfPacking typeOfPacking);

	public boolean deleteByTypeProductCode(Integer id);

	public boolean saveTypeProductCode(TypeOfPacking typeOfPacking);

	public Page<TypeOfPacking> getListTypeProductCodePaging(Pageable pageable);

	public Page<TypeOfPacking> listAllForSearch(Pageable pageable, String search);
	
	public Optional<TypeOfPacking> get(int id);
}
