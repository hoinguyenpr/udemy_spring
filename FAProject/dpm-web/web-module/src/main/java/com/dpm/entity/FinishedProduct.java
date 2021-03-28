package com.dpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author HoiNX1
 *	Modified date: 21/01/2021
 */

@Entity
@Table(name = "finished_product")
public class FinishedProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * HoiNX1 Modified date: 25/01/2021
	 * - Delete lot_name
	 * - Change lot_no to lotCodereference to product_lot(lot_code)
	 * - Add column String type. semi for finished
	 */
	
	@ManyToOne
	@JoinColumn(name = "lot_code")
	private ProductLot lotCode;
	
	//
	@Column(name = "type")
	private String type;
	
//	@Column(name = "lot_no")
//	private String lotNo;
//
//	@Column(name = "lot_name")
//	private String lotName;

	/**
	 * ThienNTN1 modify 3h30 12/01/2021 not update getter, setter, toString,
	 * constructer
	 */
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "note")
	private String note;
	
	public FinishedProduct() {
	}
	
	

	public FinishedProduct(Integer id, ProductLot lotCode, String type, Product product, String note) {
		super();
		this.id = id;
		this.lotCode = lotCode;
		this.type = type;
		this.product = product;
		this.note = note;
	}
	
	public FinishedProduct(ProductLot lotCode, String type, Product product, String note) {
		super();
		this.lotCode = lotCode;
		this.type = type;
		this.product = product;
		this.note = note;
	}
	



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductLot getLotCode() {
		return lotCode;
	}

	public void setLotCode(ProductLot lotCode) {
		this.lotCode = lotCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "FinishedProduct [id=" + id + ", lotCode=" + lotCode + ", type=" + type + ", product=" + product
				+ ", note=" + note + "]";
	}
	
	
	
	
}
