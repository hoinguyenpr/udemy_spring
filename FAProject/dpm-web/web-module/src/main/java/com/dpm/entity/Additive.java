package com.dpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "additive")
public class Additive implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "additive_name", nullable = false)
	private String additiveName;

	@Column(name = "additive_code", nullable = false, unique = true)
	private String additiveCode;

	public Additive() {
		super();
	}

	public Additive(Integer id, String additiveName, String additiveCode) {
		super();
		this.id = id;
		this.additiveName = additiveName;
		this.additiveCode = additiveCode;
	}
	
	//Nguyennd6 
	public Additive( String additiveName, String additiveCode) {
		
		this.additiveName = additiveName;
		this.additiveCode = additiveCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdditiveName() {
		return additiveName;
	}

	public void setAdditiveName(String additiveName) {
		this.additiveName = additiveName;
	}

	public String getAdditiveCode() {
		return additiveCode;
	}

	public void setAdditiveCode(String additiveCode) {
		this.additiveCode = additiveCode;
	}

	@Override
	public String toString() {
		return "Additive [id=" + id + ", additiveName=" + additiveName + ", additiveCode="
				+ additiveCode + "]";
	}

}
