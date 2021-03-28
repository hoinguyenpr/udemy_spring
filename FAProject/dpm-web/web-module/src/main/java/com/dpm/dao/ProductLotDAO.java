package com.dpm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;

@Repository
public interface ProductLotDAO extends JpaRepository<ProductLot, Integer> {

	
	
	Optional<ProductLot> findByLotCode(String lotCode);

	// Modify by DinhDN 15-01-2021 03:13:00
	@Query("SELECT alias FROM ProductLot alias WHERE alias.lotCode = ?1")
	Optional<ProductLot> findByProductLotCode(String lotCode);

	
	// Modify by LamPQT 15-01-2021 08:50:00
//	@Modifying
	@Query(value="select * FROM product_lot alias WHERE alias.type_id = :type_id",nativeQuery = true)
	List<ProductLot> getAllProductLotByTypeId(@Param(value = "type_id") int type_id);
	
	//HoiNX1
	ProductLot getProductLotByLotCode(String lotCode); 
}

