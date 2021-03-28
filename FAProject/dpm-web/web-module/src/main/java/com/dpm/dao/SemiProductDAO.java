package com.dpm.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.SemiProduct;

/**
 * Author:  HoiNX1        Modified date: 14/01/2020 11:37 AM     
 * Modifier: 
 */



@Repository
public interface SemiProductDAO extends JpaRepository<SemiProduct, Integer> {
	@Query("SELECT e FROM SemiProduct e")
	public Page<SemiProduct> findAll(Pageable pageable);

	@Query("SELECT a FROM SemiProduct a WHERE "
			+ "CONCAT(a.lotNo,' ', a.lotName)" + "LIKE %?2%")
	public Page<SemiProduct> search(Pageable pageable, String keyword);
}
