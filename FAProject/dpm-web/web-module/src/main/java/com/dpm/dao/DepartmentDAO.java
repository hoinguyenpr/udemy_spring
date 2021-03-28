package com.dpm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Department;

@Repository
public interface DepartmentDAO extends JpaRepository<Department, String> {

	@Query("SELECT e FROM Department e")
	public Page<Department> findAll(Pageable pageable);

	@Query("SELECT a FROM Department a WHERE "
			+ "CONCAT(a.departmentCode,' ', a.departmentName,' ', a.manager)"
			+ "LIKE %?1%")
	public Page<Department> findBySearch(String keyword, Pageable pageable);

	// update by DinhDN 15:26:00
	@Query("SELECT dept FROM Department dept WHERE dept.departmentCode LIKE %?1%")
	public Optional<Department> findByDeptCode(String departmentCode);
	
	// Thuanlv6 added at 3:03 PM 21/01
	List<Department> findByDepartmentCodeStartingWith(String prefix);
}