package com.dpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Author:  HoiNX1        Modified date: 11/01/2021 11:37 AM     
 * Modifier: 
 */

@Entity
@Table(name = "type_packing", uniqueConstraints = @UniqueConstraint(columnNames = {
		"typepacking_code" }))
public class TypeOfPacking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "typepacking_code")
	private String typePackingCode;

	@Column(name = "typepacking_name")
	private String typePackingName;

	@Column(name = "note")
	private String note;

	public TypeOfPacking() {
		super();
	}

	public TypeOfPacking(Integer id, String typePackingCode, String typePackingName,
			String note) {
		super();
		this.id = id;
		this.typePackingCode = typePackingCode;
		this.typePackingName = typePackingName;
		this.note = note;
	}
	
	public TypeOfPacking(String typePackingCode, String typePackingName,String note) {
		super();
		this.typePackingCode = typePackingCode;
		this.typePackingName = typePackingName;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypePackingCode() {
		return typePackingCode;
	}

	public void setTypePackingCode(String typePackingCode) {
		this.typePackingCode = typePackingCode;
	}

	public String getTypePackingName() {
		return typePackingName;
	}

	public void setTypePackingName(String typePackingName) {
		this.typePackingName = typePackingName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TypeOfPacking [id=" + id + ", typePackingCode=" + typePackingCode
				+ ", typePackingName=" + typePackingName + ", note=" + note + "]";
	}

}
