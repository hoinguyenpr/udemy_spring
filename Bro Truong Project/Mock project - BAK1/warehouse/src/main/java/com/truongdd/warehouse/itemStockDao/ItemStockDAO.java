package com.truongdd.warehouse.itemStockDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.truongdd.warehouse.entity.ItemStock;

@Repository
public interface ItemStockDAO extends JpaRepository<ItemStock, Integer>{
	
	ItemStock getByPartNumber(String partNumber);
	
	@Query(value = "SELECT IF(exists(SELECT * FROM `demo`.`part_detail` pd WHERE pd.`part_number` = :partNumber), 'true', 'false')", nativeQuery = true)
	boolean isValidPart(@Param(value = "partNumber") String partNumber);
	
	boolean existsByPartNumber(String partNumber);	
	
}
