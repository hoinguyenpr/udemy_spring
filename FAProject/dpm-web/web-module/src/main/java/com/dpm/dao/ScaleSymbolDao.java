package com.dpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.ScaleSymbol;
/**
 * 
 * @author ThuanLV6
 *
 */
@Repository
public interface ScaleSymbolDao extends JpaRepository<ScaleSymbol, Integer> {
	ScaleSymbol findBysymbolString(String symbolString);
}
