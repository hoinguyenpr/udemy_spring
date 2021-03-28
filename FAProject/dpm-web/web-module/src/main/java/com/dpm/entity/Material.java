package com.dpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
	// Nguyennd6
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "material_name", nullable = false)
	private String materialName;

	@Column(name = "material_code", nullable = false, unique = true)
	private String materialCode;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Material() {

	}

	public Material(Integer id, String materialName, String materialCode,
			Integer amount) {
		this.id = id;
		this.materialName = materialName;
		this.materialCode = materialCode;
		this.amount = amount;
	}

}
