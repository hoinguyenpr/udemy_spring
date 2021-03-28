package com.example.demo.partServiceDAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

}
