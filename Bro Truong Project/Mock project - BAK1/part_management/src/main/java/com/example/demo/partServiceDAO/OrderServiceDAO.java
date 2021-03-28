package com.example.demo.partServiceDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ItemOrder;

@Repository
public interface OrderServiceDAO extends JpaRepository<ItemOrder, Integer>{
	
}
