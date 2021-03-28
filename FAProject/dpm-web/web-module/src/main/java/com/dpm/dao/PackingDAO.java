package com.dpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Packing;

@Repository
public interface PackingDAO extends JpaRepository<Packing, Integer> {
	//HoiNX1
	Packing getByPackingCode(String packingCode);
}
