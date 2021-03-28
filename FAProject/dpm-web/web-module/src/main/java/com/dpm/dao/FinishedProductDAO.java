package com.dpm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.FinishedProduct;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;

@Repository
public interface FinishedProductDAO extends JpaRepository<FinishedProduct, Integer> {
	@Query("SELECT e FROM FinishedProduct e")
	public Page<FinishedProduct> findAll(Pageable pageable);

	@Query("SELECT a FROM FinishedProduct a WHERE "
			+ "CONCAT(a.lotCode)" + "LIKE %?1%")
	public Page<FinishedProduct> search(Pageable pageable, String keyword);
	
	/*
	 * HoiNX1 25/01/2021
	 * */
	
	@Query("SELECT fnproduct FROM FinishedProduct fnproduct WHERE fnproduct.type=?1")
	public List<FinishedProduct> findAllByType(String type);
	
	public FinishedProduct findByLotCodeAndType(ProductLot lotCode, String type);
}
