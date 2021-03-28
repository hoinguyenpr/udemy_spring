package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author LamPQT Create 1/12/21
 * 
 */
@Entity
@Table(name = "product_lot")
public class ProductLot implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "lot_code", unique = true)
	private String lotCode;

	@Column(name = "date_create")
	private LocalDateTime dateCreate;

	@Column(name = "quantity", columnDefinition = "integer default 0")
	private int quantity;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private TypeProduct typeProduct;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "create_id")
	private Employee create;

	public ProductLot() {
		super();
	}

	// update by DinhDN 15-01-2021 01:11:00
	public ProductLot(String lotCode, LocalDateTime dateCreate, int quantity,
			TypeProduct typeProduct, Employee create) {
		super();
		this.lotCode = lotCode;
		this.dateCreate = dateCreate;
		this.quantity = quantity;
		this.typeProduct = typeProduct;
		this.create = create;
	}

	public ProductLot(Integer id, String lotCode, LocalDateTime dateCreate, int quantity,
			TypeProduct typeProduct, Employee create) {
		super();
		this.id = id;
		this.lotCode = lotCode;
		this.dateCreate = dateCreate;
		this.quantity = quantity;
		this.typeProduct = typeProduct;
		this.create = create;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public LocalDateTime getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(LocalDateTime dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
	}

	public Employee getCreate() {
		return create;
	}

	public void setCreate(Employee create) {
		this.create = create;
	}

	@Override
	public String toString() {
		return "ProductLot [id=" + id + ", lotCode=" + lotCode + ", dateCreate="
				+ dateCreate + ", quantity=" + quantity + ", typeProduct=" + typeProduct
				+ ", create=" + create + "]";
	}

}
