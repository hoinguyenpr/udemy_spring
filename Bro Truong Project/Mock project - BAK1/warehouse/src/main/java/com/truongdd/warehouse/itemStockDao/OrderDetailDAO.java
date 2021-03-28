package com.truongdd.warehouse.itemStockDao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truongdd.warehouse.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

}
