package com.dpm.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ThuanLV6 created 1/12/2021
 * 
 */
import com.dpm.entity.ScaleCalibration;

@Repository
public interface ScaleCalibrationDAO extends JpaRepository<ScaleCalibration, Integer> {
	Page<ScaleCalibration> findAllByIsDeletedFalse( Pageable pagebale);
}
