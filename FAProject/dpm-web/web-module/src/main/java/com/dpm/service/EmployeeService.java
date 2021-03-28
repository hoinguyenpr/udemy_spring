
package com.dpm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.entity.Employee;

public interface EmployeeService {

	// Update by DinhDN 15-01-2021 03:00:00
	/**
	 * Function create or update success new Employee
	 * 
	 * @param employee
	 * @return True if create or update success, otherwise return false.
	 */
	public Boolean saveOrUpdateEmployee(Employee employee);

	public void deleteEmployee(Integer id);

	// Update by DinhDN 20-01-2021 04:24:00 AM
	/**
	 * Try get Employee by EmployeeId. If EmployeeId is exists in database return
	 * object Employee. otherwise if error write error to log file.
	 * 
	 * @param employeeId
	 * @return Object Employee
	 */
	public Employee getEmployeeById(Integer employeeId);

	public Employee getEmployeeByUsername(String username);

	public Page<Employee> ListAllPaging(Pageable pageable);

	public Page<Employee> listAllForSearch(Pageable pageable, String search);

	// Modify by LamPQT
	public List<Employee> findByPosition(String worker);

	// Modify by LamPQT
	public Optional<Employee> get(int qcId);

	// Modify by NguyenND6 14/01/2021 11:00 AM
	// modify by DinhDN 15-01-2021 03:19:00
	/**
	 * Function get all Employee from database.
	 * 
	 * @return List object Employee
	 */
	public List<Employee> getAllEmployee();

	// Update by DinhDN 15-01-2021 18:49:00
	/**
	 * Function get all employee with Department name
	 * 
	 * @param department
	 * @return List object Employee
	 */
	public List<Employee> getAllByDepartment(String department);

	// Update by DinhDN 30-01-2021 19:16:00 AM
	/**
	 * Try get Employee by username. If username is exists in database return object
	 * Employee. otherwise if error write error to log file.
	 * 
	 * @param username
	 * @return Object Employee.
	 */
	public Employee getEmployeeByUserName(String username);

}
