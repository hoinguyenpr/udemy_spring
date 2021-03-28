package com.dpm.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", unique = true)
	@NotEmpty(message = "Username không được rỗng")
	@Size(max = 12, min = 4, message = "Username không quá 12 ký tự và nhiều hơn 3 ký tự")
	private String username;

	private String password;

	// modify by DinhDN 15-01-2021 03:31:00
	@Column(name = "employee_name", unique = true)
	private String employeeName;

	@Column(name = "position")
	private String position;

	@Column(name = "department")
	private String department;

	@Column(name = "managerment")
	private String managerment;

	@Column(name = "note")
	private String note;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role = new HashSet<Role>();

	public Employee() {
		super();
	}

	public Employee(Integer id, String username, String password, String employeeName, String position,
			String department, String managerment, String note, Set<Role> role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.employeeName = employeeName;
		this.position = position;
		this.department = department;
		this.managerment = managerment;
		this.note = note;
		this.role = role;
	}

	// Update by DinhDN 15-01-2021 01:21:00
	public Employee(String employeeName, String username, String position, String department, String managerment,
			String note) {
		this.employeeName = employeeName;
		// thuanlv6 modified to add username to constructor
		this.username = username;
		this.position = position;
		this.department = department;
		this.managerment = managerment;
		this.note = note;
	}

	public Employee(Integer id, String employeeName, String position, String department, String managerment,
			String note) {
		this.id = id;
		this.employeeName = employeeName;
		this.position = position;
		this.department = department;
		this.managerment = managerment;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getManagerment() {
		return managerment;
	}

	public void setManagerment(String managerment) {
		this.managerment = managerment;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Employee that = (Employee) o;
		return id == that.id && Objects.equals(employeeName, that.employeeName)
				&& Objects.equals(position, that.position) && Objects.equals(department, that.department)
				&& Objects.equals(managerment, that.managerment) && Objects.equals(note, that.note);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, employeeName, position, department, managerment, note);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", position=" + position + ", department="
				+ department + ", managerment=" + managerment + ", note=" + note + "]";
	}

}