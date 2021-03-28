package com.truongdd.warehouse.itemStockDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongdd.warehouse.entity.ItemOrder;

@Repository
public interface OrderServiceDAO extends JpaRepository<ItemOrder, Integer>{
	
}
