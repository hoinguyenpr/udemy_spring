package com.dpm.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Update: Nguyennd6 Create date: 01/11/2020 16:00 AM
 **/

@Entity

@Table(name = "mixing_material")
@SQLDelete(sql = "UPDATE mixing_material SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class MixingMaterial  {

	/**
	 * 
	 */
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "status", columnDefinition = "Boolean default false")
	private Boolean status = false;

	@Column(name = "deleted", columnDefinition = "Boolean default false")
	private Boolean deleted = false;

	@Column(name = "bactch", nullable = false)
	@NotNull(message = "Null batch!")
	private Integer batch;

	@Column(name = "amount_material")
	@NotNull
	private float amountMaterial;

	@Column(name = "mixing_time")
	@NotNull
	private float mixingTime;

	@Column(name = "date_mixing")
	@NotNull
	private LocalDate dateMixing;

	@Column(name = "amount_additive")
	@NotNull
	private float amountAddtitive;

	@Column(name = "No")
	@NotNull
	private int no;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "type_id")
	private TypeProduct typeProduct;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "machine_id")
	private Machine machine;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "ingredient_id")
	private IngredientBatch ingredientBatch;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "id_employee")
	private Employee employee;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "code_produce_lot")
	private ProductLot productLot;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "code_additive")
	private Additive additive;

	public MixingMaterial(@NotNull Integer batch, float amountMaterial, float mixingTime, @NotNull LocalDate dateMixing,
			@NotNull float amountAddtitive, int no, TypeProduct typeProduct, Machine machine,
			IngredientBatch ingredientBatch, Employee employee, ProductLot productLot, Additive additive) {
		
		this.batch = batch;
		this.amountMaterial = amountMaterial;
		this.mixingTime = mixingTime;
		this.dateMixing = dateMixing;
		this.amountAddtitive = amountAddtitive;
		this.no = no;
		this.typeProduct = typeProduct;
		this.machine = machine;
		this.ingredientBatch = ingredientBatch;
		this.employee = employee;
		this.productLot = productLot;
		this.additive = additive;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Additive getAdditive() {
		return additive;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setAdditive(Additive additive) {
		this.additive = additive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	public float getAmountMaterial() {
		return amountMaterial;
	}

	public void setAmountMaterial(float amountMaterial) {
		this.amountMaterial = amountMaterial;
	}

	public float getMixingTime() {
		return mixingTime;
	}

	public void setMixingTime(float mixingTime) {
		this.mixingTime = mixingTime;
	}

	public LocalDate getDateMixing() {
		return dateMixing;
	}

	public void setDateMixing(LocalDate dateMixing) {
		this.dateMixing = dateMixing;
	}

	public float getAmountAddtitive() {
		return amountAddtitive;
	}

	public void setAmountAddtitive(float amountAddtitive) {
		this.amountAddtitive = amountAddtitive;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public IngredientBatch getIngredientBatch() {
		return ingredientBatch;
	}

	public void setIngredientBatch(IngredientBatch ingredientBatch) {
		this.ingredientBatch = ingredientBatch;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ProductLot getProductLot() {
		return productLot;
	}

	public void setProductLot(ProductLot productLot) {
		this.productLot = productLot;
	}

	

	public MixingMaterial() {

	}

}
