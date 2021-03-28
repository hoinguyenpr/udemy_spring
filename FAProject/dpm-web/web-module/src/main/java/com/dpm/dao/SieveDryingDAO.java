package com.dpm.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dpm.entity.SieveDrying;

@Repository
public interface SieveDryingDAO extends JpaRepository<SieveDrying, Integer> {

	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status` NOT IN ('Removed','Default','Approved') ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	Page<SieveDrying> getAllAvailable(Pageable pageable);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status` NOT IN ('Removed','Default','Approved') ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	List<SieveDrying> getAllAvailable();
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status` = 'Default' ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	SieveDrying getDefault();
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status` NOT IN ('Removed','Default') ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	List<SieveDrying> getAllButNotDefault();
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status`= 'Pending' ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	List<SieveDrying> getPending();
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` WHERE `sieve_drying`.`status`= 'Pending' ORDER BY `sieve_drying`.`input_date` DESC", nativeQuery = true)
	Page<SieveDrying> getPending(Pageable pageable);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` sd WHERE sd.`status` NOT IN ('Removed','Default')", nativeQuery = true)
	Page<SieveDrying> getAllButNotDefault(Pageable pageable);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` sd\r\n" + 
			"WHERE (sd.`status` NOT IN ('Removed','Default'))\r\n" + 
			"AND (sd.input_date between (select CAST(DATE_FORMAT(NOW() ,'%Y-%m-01') as DATE)) and NOW())", nativeQuery = true)
	Page<SieveDrying> getAllButNotDefaultForReport(Pageable pageable);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` sd\r\n" + 
			"WHERE (sd.`status` NOT IN ('Removed','Default'))\r\n" + 
			"AND (sd.input_date = :date)", nativeQuery = true)
	Page<SieveDrying> getAllRecordsByDate(Pageable pageable, @Param(value = "date") LocalDate date);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` sd WHERE sd.`status` = 'Approved' ORDER BY sd.`input_date` DESC", nativeQuery = true)
	Page<SieveDrying> getApproved(Pageable pageable);
	
	@Query(value = "SELECT * FROM `qams`.`sieve_drying` sd WHERE sd.`status` IN ('Approved','Pending') ORDER BY sd.`input_date` DESC", nativeQuery = true)
	Page<SieveDrying> getApprovedAndPending(Pageable pageable);
}
