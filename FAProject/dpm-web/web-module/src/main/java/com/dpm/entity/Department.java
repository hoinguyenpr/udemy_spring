package com.dpm.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author DinhDN Update: 12/01/2021
 *
 */

@Entity
@Table(name = "department")
public class Department {

	@Id
	@Column(name = "department_code", unique = true)
	private String departmentCode;

	@Column(name = "department_name")
	private String departmentName;

	@Column(name = "department_manager")
	private String manager;

	public Department() {
		super();
	}

	public Department(String departmentCode, String departmentName, String manager) {
		super();
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.manager = manager;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Department that = (Department) o;
		return Objects.equals(departmentCode, that.departmentCode)
				&& Objects.equals(departmentName, that.departmentName)
				&& Objects.equals(manager, that.manager);
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentCode, departmentName, manager);
	}

	// update by DinhDN 13:28:00
	@Override
	public String toString() {
		return "Department [departmentCode=" + departmentCode + ", departmentName="
				+ departmentName + ", manager=" + manager + "]";
	}
	
	

}