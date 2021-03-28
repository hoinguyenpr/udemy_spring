package com.dpm.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dpm.entity.CheckList;
import com.dpm.utility.Status;

@Repository
public interface CheckListDAO
		extends JpaRepository<CheckList, Integer>, PagingAndSortingRepository<CheckList, Integer> {

	public List<CheckList> findByDate(LocalDate date);
	
	public CheckList findByStatus(Status status);
	
	
	@Modifying
	@Query(value = "update CheckList c set c.status = :status where c.id  = :id")
	public void updateStatus(@Param(value = "id") int id, @Param(value = "status") Status status);
	
	@Query(value = "SELECT c FROM CheckList c WHERE c.status NOT IN ('Removed')")
	public Page<CheckList> findAll(Pageable pageable);

	@Query(value = "SELECT c FROM CheckList c WHERE FUNCTION('DATEDIFF',FUNCTION('CURRENT_DATE '),c.date) <=30 AND c.status NOT IN ('Removed')")
	public Page<CheckList> findInMonth(Pageable pageable);
	
	@Query(value = "SELECT c FROM CheckList c WHERE c.status NOT IN ('Removed') AND c.date = :date "		
			+ " AND (c.color = :isOk OR c.taste = :isOk) ")
	public Page<CheckList> findAllWithOkOrNG(@Param(value="date") LocalDate date, @Param(value="isOk")  boolean isOk,  Pageable pageable);
	
	@Query(value = "SELECT c FROM CheckList c WHERE c.status NOT IN ('Removed') AND c.date = :date "
			+ " AND CONCAT("
			+ "c.moisture, ' ', "
			+ "c.ph, ' ', "
			+ "c.brix, ' ', "
			+ "c.impurity, ' ', "
			+ "c.quantitySatisfied, ' ', "
			+ "c.quantityUnsatisfied, ' ', "
			+ "c.quantityMix, ' ', "
			+ "c.remark , "
			+ "c.qc.username,"
			+ "c.qc.employeeName,"
			+ "c.lot.typeProduct.typeProductName)  LIKE %:keyword%")
	public Page<CheckList> findAll(@Param(value="date") LocalDate date, @Param(value="keyword") String keyword, Pageable pageable);
	
	
	
//	@Query(value = "SELECT * FROM checklists c " + "where date_create = :date " + "and lot_id = :lot "
//			+ "and c.time_create between :fromTime and :toTime "
//			+ "and c.status not in ('Removed','Rejected','Default')", nativeQuery = true)
//	public Page<CheckList> filter(@Param(value = "fromTime") LocalTime fromTime,
//			@Param(value = "toTime") LocalTime toTime, @Param(value = "date") LocalDate date,
//			@Param(value = "lot") int lot, Pageable pageable);
//
//	@Query(value = "SELECT * FROM checklists c " + "where date_create = :date " + "and lot_id = :lot "
//			+ "and c.time_create between :fromTime and :toTime "
//			+ "and c.status not in ('Removed','Rejected','Default')" + "order by c.time_create", nativeQuery = true)
//	public List<CheckList> report(@Param(value = "fromTime") LocalTime fromTime,
//			@Param(value = "toTime") LocalTime toTime, @Param(value = "date") LocalDate date,
//			@Param(value = "lot") int lot);
//
//	@Query(value = "select c.* from checklists as c " + "join product_lot as p on c.lot_id = p.id "
//			+ "join type_product as t on t.id = p.type_id " + "where c.date_create = :date "
//			+ "and c.time_create between :fromTime and :toTime " + "and p.type_id = :typeProduct "
//			+ "order by c.time_create", nativeQuery = true)
//	public Page<CheckList> filterByTypeLotAll(@Param(value = "date") LocalDate date,
//			@Param(value = "typeProduct") Integer typeProduct, @Param(value = "fromTime") LocalTime fromTime,
//			@Param(value = "toTime") LocalTime toTime, Pageable pageable);
//
//	@Query(value = "SELECT * FROM checklists c " + "where date_create = :date "
//			+ "and c.time_create between :fromTime and :toTime "
//			+ "and c.status not in ('Removed','Rejected','Default')" + "order by c.time_create", nativeQuery = true)
//	
//	public Page<CheckList> filterByAll(@Param(value = "fromTime") LocalTime fromTime,
//			@Param(value = "toTime") LocalTime toTime, @Param(value = "date") LocalDate date, Pageable pageable);
//
	

}
