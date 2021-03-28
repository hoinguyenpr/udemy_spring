/**
 * 
 */
package com.dpm.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.MetalDetector;

/**
 * @author DinhDN 13/01/2021
 *
 */

@Repository
public interface MetalDetectorDAO extends JpaRepository<MetalDetector, Integer> {
	
	@Query("SELECT metalDetector FROM MetalDetector AS metalDetector "
			+ " WHERE metalDetector.isDelete = 0 "
			+ " ORDER BY metalDetector.modifyDate DESC")
	Page<MetalDetector> findAllMetalDetectorAvailable(Pageable pageable);

	@Query("SELECT metalDetector FROM MetalDetector AS metalDetector "
			+ " WHERE metalDetector.isDelete = 0 "
			+ " ORDER BY " 
			+ "     CASE "
			+ "          WHEN metalDetector.status = 'Pending' THEN 1 "
			+ "          WHEN metalDetector.status = 'Rejected' THEN 2 " 
			+ "          ELSE 3 " 
			+ "     END ")
	Page<MetalDetector> getAllSortByStatus(Pageable pageable);

	@Query("SELECT metalDetector FROM MetalDetector AS metalDetector "
			+ " WHERE metalDetector.isDelete = 0 "
			+ "     AND metalDetector.checkingDate >= ?1 "
			+ "     AND metalDetector.checkingDate <= ?2  "
			+ " ORDER BY metalDetector.modifyDate DESC ")
	Page<MetalDetector> findMetalDetectorByKeyword(Date startDate, Date endDate, Pageable pageable);

	@Query("SELECT metalDetector FROM MetalDetector AS metalDetector "
			+ " WHERE metalDetector.isDelete = 0 "
			+ "     AND metalDetector.checkingDate >= ?1 AND metalDetector.checkingDate <= ?2  "
			+ "     AND ( productLot.lotCode LIKE %?3%" 
			+ "           OR department.departmentCode LIKE %?3%"
			+ "           OR inspector.employeeName LIKE %?3% )" 
			+ " ORDER BY metalDetector.modifyDate DESC ")
	Page<MetalDetector> findMetalDetectorByKeyword(Date startDate, Date endDate, String keyword, Pageable pageable);

}
