package com.dpm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpm.dao.ScaleSymbolDao;
import com.dpm.entity.ScaleSymbol;
import com.dpm.service.ScaleSymbolService;
/**
 * 
 * @author ThuanLV6
 *
 */
@Service
public class ScaleSymbolServiceImpl implements ScaleSymbolService {

	@Autowired
	private ScaleSymbolDao dao;
	
	@Override
	public List<ScaleSymbol> findAll() {
		
		return dao.findAll();
	}

	@Override
	public ScaleSymbol findByName(String name) {
		return dao.findBysymbolString(name);
		
	}

	@Override
	public ScaleSymbol findById(Integer id) {
	
		return dao.findById(id).get();
	}

	@Override
	public ScaleSymbol save(ScaleSymbol scaleSymbol) {
		
		return dao.save(scaleSymbol);
	}

}
