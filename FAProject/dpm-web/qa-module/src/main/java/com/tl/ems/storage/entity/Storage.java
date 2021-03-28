package com.tl.ems.storage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * The storage class is an entity class. This class mapping with "storage" table in the database
 * @author DangVTH
 *
 */
@Entity(name = "storage")
public class Storage {
	
	/**
	 * Id of storage. It's primary key
	 */
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * Name of storage
	 */
	@Column(name = "name")
	@NotBlank
	private String name; 
	
	/**
	 * Company Id of storage, use to get company's name of storage
	 */
	@Column(name = "companyId")
	private Integer companyId;
	
	/**
	 * Getter, use to get id of storage
	 * @return Integer
	 */
	public Integer getId() {
	
		return id;
		
	}
	
	/**
	 * Setter, use to set id for storage
	 * @param id
	 */
	public void setId(Integer id) {
		
		this.id = id;
		
	}
	
	/**
	 * Getter, use to get name of storage
	 * @return String
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * Setter, use to set name for storage
	 * @param name
	 */
	public void setName(String name) {
		
		this.name = name;
		
	}
	
	/**
	 * Getter, use to get id of company
	 * @return Integer
	 */
	public Integer getCompanyId() {
		
		return companyId;
		
	}

	/**
	 * Setter, use to set company's id for storage 
	 * @param companyId
	 */
	public void setCompanyId(Integer companyId) {
		
		this.companyId = companyId;
		
	}

	/**
	 * Constructor, initialize new storage with storage's name
	 * @param name
	 */
	public Storage(String name, Integer companyId) {
		
		this.name = name;
		this.companyId = companyId;
		
	}
	
	/**
	 * Constructor, initialize new null storage 
	 */
	public Storage() {
		
	}

	/**
	 * toString method use to get information of storage
	 */
	@Override
	public String toString() {
		return "Storage [id=" + id + ", name=" + name + ", companyId=" + companyId + "]";
	}
	
	
	
	
	
}
