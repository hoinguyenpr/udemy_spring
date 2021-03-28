package com.dpm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.Department;

public interface DepartmentService {

	/**
	 * Function create or update if departmentCode not exists in database.
	 * 
	 * @param department
	 * @return True if create or update success, otherwise false.
	 */
	public Boolean saveOrUpdate(Department department);

	public void deleteDepartment(String departmentCode);

	// Update By DinhDN 20-01-2021 16:40:00
	/**
	 * Try get Department by departmentCode. If departmentCode is exists in database
	 * return object Department. otherwise if error write error to log file and
	 * return null.
	 * 
	 * @param departmentCode
	 * @return Object Department;
	 */
	public Department getDepartmentById(String departmentCode);

	public Page<Department> ListAllPaging(Pageable pageable);

	public Page<Department> listAllForSearch(Pageable pageable, String search);

	// update by DinhDN
	/**
	 * Function get all department from database
	 * 
	 * @return List object Department
	 */
	public List<Department> getAllDepartment();
	
	// thuanlv6 added at 3:04 PM 21/01
	public List<Department> findAllByDepartmentCode(String prefix);

}