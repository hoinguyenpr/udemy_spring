package com.dpm.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Packing;
import com.dpm.entity.TypeOfPacking;

/**
 * Author:  HoiNX1        Modified date: 14/01/2021 11:37 AM     
 * Modifier: 
 */

@Repository
public interface TypeOfPackingDAO extends JpaRepository<TypeOfPacking, Integer>{
	

	@Query("SELECT e FROM TypeOfPacking e")
	Page<TypeOfPacking> findTypePackingCode(Pageable pageable);

	@Query("SELECT e FROM TypeOfPacking e WHERE e.typePackingCode = ?1")
	Optional<TypeOfPacking> findTypePackingCode(String typeProductCode);

	@Query("DELETE FROM TypeOfPacking e WHERE e.typePackingCode = ?1")
	Optional<TypeOfPacking> deleteTypePackingCode(String typeProductCode);

	@Query("SELECT a FROM TypeOfPacking a WHERE "
			+ "CONCAT(a.typePackingCode,' ', a.typePackingName,' ',a.note)" + "LIKE %?1%")
	
	public Page<TypeOfPacking> findAll(String keyword, Pageable pageable);

	public TypeOfPacking findByTypePackingCode(String typeProductCode);

	public TypeOfPacking findByTypePackingName(String typeProductName);
	
	//HoiNX1
	TypeOfPacking getByTypePackingCode(String typePackingCode);
}
