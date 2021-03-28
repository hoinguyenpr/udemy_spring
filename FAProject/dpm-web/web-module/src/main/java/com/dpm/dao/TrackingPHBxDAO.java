package com.dpm.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.TrackingPHBx;

@Repository
public interface TrackingPHBxDAO extends JpaRepository<TrackingPHBx, Integer> {

	
	Page<TrackingPHBx> findAll(Pageable pageAble);
	
	List<TrackingPHBx> findByDate(LocalDate date);
	
	Page<TrackingPHBx> findByDate(LocalDate date, Pageable pageAble);
	
	Page<TrackingPHBx> findByDateAndStatus(LocalDate date, int status, Pageable pageAble);
}
