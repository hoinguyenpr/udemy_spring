package com.dpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.dao.DepartmentDAO;
import com.dpm.entity.Department;
import com.dpm.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentDAO departmentDao;

	@Override
	public void deleteDepartment(String departmentCode) {
		departmentDao.deleteById(departmentCode);
	}

	// Update by DinhDN 14-01-2021
	/**
	 * Function create or update if departmentCode not exists in database.
	 * 
	 * @param department
	 * @return True if create or update success, otherwise false.
	 */
	@Override
	public Boolean saveOrUpdate(Department department) {
		Optional<Department> optional = null;

		try {
			// Try get department in database with parameter is departmentCode
			optional = departmentDao.findByDeptCode(department.getDepartmentCode());

			// Update department If departmentCode is exists in database.
			if (optional.isPresent()) {
				Department deptUpdate = optional.get();

				deptUpdate.setDepartmentName(department.getDepartmentName());
				deptUpdate.setManager(department.getManager());

				departmentDao.save(deptUpdate);
			} else {
				// If departmentCode is not exists in database then create new department.
				departmentDao.save(department);
			}
			return true;

		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return false;

	}

	// Update By DinhDN 20-01-2021 16:40:00
	/**
	 * Try get Department by departmentCode. If departmentCode is exists in database
	 * return object Department. otherwise if error write error to log file and
	 * return null.
	 * 
	 * @param departmentCode
	 * @return Object Department;
	 */
	@Override
	public Department getDepartmentById(String departmentCode) {

		Department department = new Department();

		// Try get Department by departmentCode. If departmentCode is exists in
		// database return object Department. otherwise if error write error to log
		// file and return null.
		try {
			department = departmentDao.findByDeptCode(departmentCode).get();
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}
		return department;

	}

	@Override
	public Page<Department> ListAllPaging(Pageable pageable) {
		return departmentDao.findAll(pageable);
	}

	@Override
	public Page<Department> listAllForSearch(Pageable pageable, String search) {
		Page<Department> listDepartment = departmentDao.findBySearch(search, pageable);
		return listDepartment;
	}

	// update by DinhDN
	/**
	 * Function get all department from database
	 * 
	 * @return List object Department
	 */
	@Override
	public List<Department> getAllDepartment() {

		// Create new list department.
		List<Department> departments = new ArrayList<Department>();

		// Try get all department from database. If can't connect to database write log
		// file to save error.
		try {
			departments = departmentDao.findAll();
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e.getMessage());
		}

		return departments;
	}

	// thuanlv6 added at 3:06 PM 21/01
	@Override
	public List<Department> findAllByDepartmentCode(String prefix) {
		return departmentDao.findByDepartmentCodeStartingWith(prefix);
		
	}

}