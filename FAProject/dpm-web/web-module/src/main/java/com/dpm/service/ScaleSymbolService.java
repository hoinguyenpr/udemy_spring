package com.dpm.service;

import java.util.List;

import com.dpm.entity.ScaleSymbol;
/**
 * 
 * @author ThuanLV6
 *
 */
public interface ScaleSymbolService {
	List<ScaleSymbol> findAll();
	ScaleSymbol findByName(String name);
	ScaleSymbol findById(Integer id);
	ScaleSymbol save(ScaleSymbol scaleSymbol);
}
