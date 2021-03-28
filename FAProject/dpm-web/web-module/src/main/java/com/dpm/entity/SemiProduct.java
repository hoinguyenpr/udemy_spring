package com.dpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Author: HoiNX1 Modified date: 20/01/2020
 */

@Entity
@Table(name = "semi_product")
public class SemiProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "lot_no")
	private String lotNo;

	@Column(name = "lot_name")
	private String lotName;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "note")
	private String note;

	public SemiProduct() {
		super();
	}
	public SemiProduct(String lot_no) {
		super();
		this.lotNo = lot_no;
	}
	public SemiProduct(Integer id, String lotNo, String lotName, Product product, String note) {
		super();
		this.id = id;
		this.lotNo = lotNo;
		this.lotName = lotName;
		this.product = product;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "SemiProduct [id=" + id + ", lotNo=" + lotNo + ", lotName=" + lotName
				+ ", product=" + product + ", note=" + note + "]";
	}

}