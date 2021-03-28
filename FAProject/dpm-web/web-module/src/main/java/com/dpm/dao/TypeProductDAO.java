package com.dpm.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.TypeProduct;

@Repository
public interface TypeProductDAO extends JpaRepository<TypeProduct, Integer> {

	// Modify by DinhDN 15-01-2021 02:25:00
	@Query("SELECT alias FROM TypeProduct alias")
	Page<TypeProduct> getAllTypeProduct(Pageable pageable);

	@Query("DELETE FROM TypeProduct e WHERE e.typeProductCode = ?1")
	Optional<TypeProduct> deleteTypeProductCode(String typeProductCode);

	// Modify by DinhDN 14-01-2021
	@Query("SELECT alias FROM TypeProduct alias WHERE "
			+ "CONCAT(alias.typeProductCode , " + " ' ' , alias.typeProductName , "
			+ "' ' , alias.note)" + "LIKE %?1%")
	public Page<TypeProduct> searchTypeProduct(String keyword, Pageable pageable);

	// Modify by DinhDN 15-01-2021 02:25:00
	@Query("SELECT alias FROM TypeProduct alias WHERE alias.typeProductCode = ?1")
	public TypeProduct findByTypeProductCode(String typeProductCode);

	public TypeProduct findByTypeProductName(String typeProductName);
	
	//HoiNX1 
	public TypeProduct getByTypeProductCode(String typeProductCode);

}
