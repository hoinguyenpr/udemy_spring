package com.dpm.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "type_product")
public class TypeProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// modify by DinhDN 14-01-2021
	@Column(name = "type_product_code", unique = true)
	@Size(max = 30, message = "Length is require max 30 character.")

	private String typeProductCode;

	// modify by DinhDN 14-01-2021
	@Column(name = "type_product_name")
	@Size(max = 150, message = "Length is require max 150 character.")

	private String typeProductName;

	@Column(name = "note")
	private String note;

	// ThienNTN1 modify 3h30 12/01/2021
	// LongVT7 modify 14/01/2021
	@OneToMany(mappedBy = "typeProduct", fetch = FetchType.EAGER)
	private Set<Product> products = new HashSet<Product>();

	public TypeProduct() {
		super();
	}

	// Update by DinhDN 15-01-2021 08:00:00

	public TypeProduct(
			@Size(max = 30, message = "Length is require max 30 character.") String typeProductCode,
			@Size(max = 150, message = "Length is require max 150 character.") String typeProductName,
			String note) {
		super();
		this.typeProductCode = typeProductCode;
		this.typeProductName = typeProductName;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeProductCode() {
		return typeProductCode;
	}

	public void setTypeProductCode(String typeProductCode) {
		this.typeProductCode = typeProductCode;
	}

	public String getTypeProductName() {
		return typeProductName;
	}

	public void setTypeProductName(String typeProductName) {
		this.typeProductName = typeProductName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// update by DinhDN 13:30:00
	@Override
	public String toString() {
		return "TypeProduct [id=" + id + ", typeProductCode=" + typeProductCode
				+ ", typeProductName=" + typeProductName + ", note=" + note
				+ ", products=" + products + "]";
	}

}
