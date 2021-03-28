
package com.dpm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer> {

	@Query("SELECT e FROM Employee e")
	public Page<Employee> findAll(Pageable pageable);

	@Query("SELECT a FROM Employee a WHERE "
			+ "CONCAT(a.id,' ', a.employeeName,' ', a.position,' ', a.department,' ', a.managerment,' ', a.note)"
			+ "LIKE %?1%")
	public Page<Employee> findBySearch(String keyword, Pageable pageable);

	// Modify by LamPQT 1/12/21
	public List<Employee> findByPosition(String position);

	// Modify by NguyenND6 14/01/2021 14:30
	// modify by DinhDN 15-01-2021 10:34:00
	@Query("SELECT alias FROM Employee alias WHERE alias.employeeName = ?1")
	public Employee findByEmployeeName(String name);

	/**
	 * Thuanlv6 added getAllByDepartmentName 1/15 8:56 AM
	 * 
	 * @param departmentName
	 * @return
	 */
	@Query("SELECT employee FROM Employee employee WHERE employee.department = ?1")
	public List<Employee> getAllByDepartmentName(String departmentName);
	
	/**
	 * @param LongVT7 19/01/2021 09:17
	 * @return Employee
	 */
	public Employee findEmployeeByUsername(String username);
	
	// Nguyennd6 findByName 20/01/2021  16:30
	
	List<Employee> findByEmployeeNameContainingIgnoreCase(String name);
	
	
}
