package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.EmployeeDAO;
import com.dpm.dao.RoleDAO;
import com.dpm.entity.Employee;
import com.dpm.entity.Role;
import com.dpm.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	// Update by DinhDN 21-01-2021 04:31:00
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDAO employeeDao;

	@Autowired
	private RoleDAO roleDao;

	// Update by DinhDN 15-01-2021 03:00:00
	// Update by DinhDN 15-01-2021 10:36:00
	/**
	 * Function create or update success new Employee
	 * 
	 * @param employee
	 * @return True if create or update success, otherwise return false.
	 */
	@Override
	public Boolean saveOrUpdateEmployee(Employee employee) {

		Employee employeeUpdate = null;
		try {
			// Try get Employee in database with parameter is EmployeeName
			employeeUpdate = employeeDao.findByEmployeeName(employee.getEmployeeName());

			// Update Employee If EmployeeName is exists in database.
			if (employeeUpdate != null) {
				employeeUpdate.setPosition(employee.getPosition());
				employeeUpdate.setDepartment(employee.getDepartment());
				employeeUpdate.setManagerment(employee.getManagerment());
				employeeUpdate.setNote(employee.getNote());

				Role role = roleDao.findRoleByRole("ADMIN");
				employee.setRole(new HashSet<Role>(Arrays.asList(role)));

				employeeDao.save(employeeUpdate);

			} else {
				// If EmployeeName is not exists in database then create new Employee.
				employeeDao.save(employee);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return false;

	}

	@Override
	public void deleteEmployee(Integer id) {
		employeeDao.deleteById(id);

	}

	// Update by DinhDN 21-01-2021 04:24:00 AM
	/**
	 * Try get Employee by EmployeeId. If EmployeeId is exists in database return
	 * object Employee. otherwise if error write error to log file.
	 * 
	 * @param employeeId
	 * @return Object Employee
	 */
	@Override
	public Employee getEmployeeById(Integer employeeId) {
		Employee employee = new Employee();
		try {
			employee = employeeDao.findById(employeeId).get();
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return employee;
	}

	@Override
	public Page<Employee> ListAllPaging(Pageable pageable) {
		return employeeDao.findAll(pageable);
	}

	@Override
	public Page<Employee> listAllForSearch(Pageable pageable, String search) {
		Page<Employee> listEmployee = employeeDao.findBySearch(search, pageable);
		return listEmployee;
	}

	// Modify by LamPQT 1/12/21
	@Override
	public List<Employee> findByPosition(String position) {
		try {
			List<Employee> employees = employeeDao.findByPosition(position);
			return employees;
		} catch (Exception e) {

		}
		return null;
	}

	// Modify by LamPQT 1/12/21
	@Override
	public Optional<Employee> get(int id) {
		return employeeDao.findById(id);
	}

	// Modify by NguyenND6 14/02/2021 11:00 AM
	// modify by DinhDN 15-01-2021 03:19:00
	/**
	 * Function get all Employee from database.
	 * 
	 * @return List object Employee
	 */
	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			employees = employeeDao.findAll();
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return employees;
	}

	// Update by DinhDN 15-01-2021 18:49:00
	/**
	 * Function get all employee with Department name
	 * 
	 * @param department
	 * @return List object Employee
	 */
	@Override
	public List<Employee> getAllByDepartment(String department) {
		List<Employee> employees = new ArrayList<Employee>();

		try {
			employees = employeeDao.getAllByDepartmentName(department);
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return employees;
	}

	/**
	 * @author LongVT7 function will get employee information by user name
	 * 
	 * edit: LongVT7 17/02/2021: Delete print console
	 * 
	 * @param String user name
	 * @return Object Employee
	 */

	@Override
	public Employee getEmployeeByUsername(String username) {
		return employeeDao.findEmployeeByUsername(username);
	}

	// Update by DinhDN 30-01-2021 19:16:00 AM
	/**
	 * Try get Employee by username. If username is exists in database return object
	 * Employee. otherwise if error write error to log file.
	 * 
	 * @param username
	 * @return Object Employee.
	 */

	@Override
	public Employee getEmployeeByUserName(String username) {
		Employee employee = new Employee();
		try {
			employee = employeeDao.findEmployeeByUsername(username);
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return employee;
	}

}
