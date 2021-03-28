package com.dpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.PackingForm;

/**
 * Author:  HoiNX1        Modified date: 14/01/2020 11:37 AM     
 * Modifier: 
 */

@Repository
public interface PackingFormDAO extends JpaRepository<PackingForm, Integer>{
	
}
