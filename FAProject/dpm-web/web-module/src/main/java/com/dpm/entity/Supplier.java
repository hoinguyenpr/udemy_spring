package com.dpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor

@Table(name = "supplier")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "supplier_code")
	private String supplierCode;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "phone_number")
	private Integer phoneNumber;

	@Column(name = "address")
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Supplier(Integer id, String supplierCode, String supplierName,
			Integer phoneNumber, String address) {
		super();
		this.id = id;
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Supplier() {
		super();
	}

}
