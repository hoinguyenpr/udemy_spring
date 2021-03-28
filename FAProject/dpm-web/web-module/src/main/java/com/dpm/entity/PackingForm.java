package com.dpm.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.EAN;
import org.springframework.format.annotation.DateTimeFormat;

import com.dpm.utility.Status;

/**
 * Author:  HoiNX1        Modified date: 25/01/2020 11:26 AM     
 */

@Entity
@Table(name = "packing_form")

public class PackingForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "type_product_id")
	private TypeProduct typeProduct;
	
	@ManyToOne
	@JoinColumn(name = "machine_id")
	private Machine machine;
	
	@Column(name = "dateCreate")
	private LocalDate packingDate;
	
	@ManyToOne
	@JoinColumn(name = "semi_product_id")
	private FinishedProduct semiProduct;

	@ManyToOne
	@JoinColumn(name = "finished_product_id")
	private FinishedProduct finishedProduct;
	
	@Column(name = "quanity")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "packing_id")
	private Packing packing;

	@ManyToOne
	@JoinColumn(name = "packing_type")
	private TypeOfPacking typeofpacking;
	
	@Column(name = "packing_quanity")
	private int packingQuanity;

	@Column(name = "shift")
	private int shift;
	
	@ManyToOne
	@JoinColumn(name = "id_createman")
	private Employee createman;
	
	@ManyToOne
	@JoinColumn(name = "id_foreman")
	private Employee foreman;

	@Column(name = "time")
	private LocalTime time;

	@ManyToOne
	@JoinColumn(name = "person_in_charge")
	private Employee personInCharge;

	@Column(name = "sttNo")
	private int sttNo;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(columnDefinition = "boolean default false", name = "is_deleted")
	private boolean isDeleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public LocalDate getPackingDate() {
		return packingDate;
	}

	public void setPackingDate(LocalDate packingDate) {
		this.packingDate = packingDate;
	}

	public FinishedProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(FinishedProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public FinishedProduct getFinishedProduct() {
		return finishedProduct;
	}

	public void setFinishedProduct(FinishedProduct finishedProduct) {
		this.finishedProduct = finishedProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Packing getPacking() {
		return packing;
	}

	public void setPacking(Packing packing) {
		this.packing = packing;
	}

	public TypeOfPacking getTypeofpacking() {
		return typeofpacking;
	}

	public void setTypeofpacking(TypeOfPacking typeofpacking) {
		this.typeofpacking = typeofpacking;
	}

	public int getPackingQuanity() {
		return packingQuanity;
	}

	public void setPackingQuanity(int packingQuanity) {
		this.packingQuanity = packingQuanity;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public Employee getCreateman() {
		return createman;
	}

	public void setCreateman(Employee createman) {
		this.createman = createman;
	}

	public Employee getForeman() {
		return foreman;
	}

	public void setForeman(Employee foreman) {
		this.foreman = foreman;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Employee getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(Employee personInCharge) {
		this.personInCharge = personInCharge;
	}

	public int getSttNo() {
		return sttNo;
	}

	public void setSttNo(int sttNo) {
		this.sttNo = sttNo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public PackingForm(TypeProduct typeProduct, Machine machine, LocalDate packingDate, FinishedProduct semiProduct,
			FinishedProduct finishedProduct, int quantity, Packing packing, TypeOfPacking typeofpacking,
			int packingQuanity, int shift, Employee createman, Employee foreman, LocalTime time,
			Employee personInCharge, int sttNo, Status status, boolean isDeleted) {
		super();
		this.typeProduct = typeProduct;
		this.machine = machine;
		this.packingDate = packingDate;
		this.semiProduct = semiProduct;
		this.finishedProduct = finishedProduct;
		this.quantity = quantity;
		this.packing = packing;
		this.typeofpacking = typeofpacking;
		this.packingQuanity = packingQuanity;
		this.shift = shift;
		this.createman = createman;
		this.foreman = foreman;
		this.time = time;
		this.personInCharge = personInCharge;
		this.sttNo = sttNo;
		this.status = status;
		this.isDeleted = isDeleted;
	}
	
	public PackingForm() {
	}

	@Override
	public String toString() {
		return "PackingForm [id=" + id + "\n, typeProduct=" + typeProduct + "\n, machine=" + machine + "\n, packingDate="
				+ packingDate + "\n, semiProduct=" + semiProduct + "\n, finishedProduct=" + finishedProduct + "\n, quantity="
				+ quantity + "\n, packing=" + packing + "\n, typeofpacking=" + typeofpacking + "\n, packingQuanity="
				+ packingQuanity + "\n, shift=" + shift + "\n, createman=" + createman + "\n, foreman=" + foreman + "\n, time="
				+ time + "\n, personInCharge=" + personInCharge + "\n, sttNo=" + sttNo + "\n, status=" + status
				+ "\n, isDeleted=" + isDeleted + "]";
	}
	
	
	
}