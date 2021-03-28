package com.dpm.entity;

import java.io.Serializable;

/**
 * author: VuDH11
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "packing")
public class Packing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "packing_code", nullable = false, unique = true)
	private String packingCode;

	@Column(name = "packing_name", nullable = false)
	private String packingName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPackingName() {
		return packingName;
	}

	public void setPackingName(String packingName) {
		this.packingName = packingName;
	}

	public String getPackingCode() {
		return packingCode;
	}

	public void setPackingCode(String packingCode) {
		this.packingCode = packingCode;
	}

	public Packing(Integer id, String packingName, String packingCode) {
		super();
		this.id = id;
		this.packingName = packingName;
		this.packingCode = packingCode;
	}
	
	public Packing(String packingName, String packingCode) {
		super();
		this.packingName = packingName;
		this.packingCode = packingCode;
	}

	public Packing() {
		super();
	}

	@Override
	public String toString() {
		return "Packing [id=" + id + ", packing=" + packingName + ", packingCode=" + packingCode + "]";
	}
	
	
}
