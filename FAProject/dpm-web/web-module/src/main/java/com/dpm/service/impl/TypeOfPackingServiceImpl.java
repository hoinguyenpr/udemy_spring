package com.dpm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.TypeOfPackingDAO;
import com.dpm.entity.TypeOfPacking;
import com.dpm.service.TypeOfPackingService;

/**
 * Author:  HoiNX1        Modified date: 14/01/2021 11:37 AM     
 * Modifier: 
 */

@Service
public class TypeOfPackingServiceImpl implements TypeOfPackingService{

	@Autowired
	TypeOfPackingDAO typeOfPackingDAO;

	@Override
	public List<TypeOfPacking> getAllTypePacking() {
		// TODO Auto-generated method stub
		return typeOfPackingDAO.findAll();
	}

	@Override
	public TypeOfPacking getTypeProductByTypeProductCode(String typeProductCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOfPacking getTypeProductByTypeProductName(String typeProductName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createOrUpdate(TypeOfPacking typeOfPacking) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean deleteByTypeProductCode(Integer id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean saveTypeProductCode(TypeOfPacking typeProduct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<TypeOfPacking> getListTypeProductCodePaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TypeOfPacking> listAllForSearch(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TypeOfPacking> get(int typeProductId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeOfPacking> saveAllRecord(List<TypeOfPacking> list) {
		// TODO Auto-generated method stub
		return typeOfPackingDAO.saveAll(list);
	}
	
	
}
