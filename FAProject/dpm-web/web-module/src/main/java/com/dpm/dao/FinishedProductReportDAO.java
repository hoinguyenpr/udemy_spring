package com.dpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.FinishedProductReport;

@Repository
public interface FinishedProductReportDAO extends JpaRepository<FinishedProductReport, Integer> {
	
}
