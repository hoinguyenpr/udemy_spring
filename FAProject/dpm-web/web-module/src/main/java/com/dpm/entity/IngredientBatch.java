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
import javax.persistence.Table;

@Entity
@Table(name = "ingredient_batch")
public class IngredientBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ingredient_batch_name")
	private String ingredientBatchName;

	@Column(name = "ingredient_batch_code")
	private String ingredientBatchCode;

	@Column(name = "ingredient_batch_import_date")
	private String ingredientBatchImportDate;

	@Column(name = "ingredient_batch_import_number")
	private String ingredientBatchImportNumber;

	@Column(name = "ingredient_batch_import_person")
	private String ingredientBatchImportPerson;

	@Column(name = "ingredient_batch_validate_person")
	private String ingredientBatchValidatePerson;

	// Add relationship supplier
	// Update by LongVT7
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier")
	private Supplier supplier;

	public IngredientBatch() {
		super();
	}

	public IngredientBatch(Integer id, String ingredientBatchName, String ingredientBatchCode,
			String ingredientBatchImportDate, String ingredientBatchImportNumber, String ingredientBatchImportPerson,
			String ingredientBatchValidatePerson, Supplier supplier) {
		super();
		this.id = id;
		this.ingredientBatchName = ingredientBatchName;
		this.ingredientBatchCode = ingredientBatchCode;
		this.ingredientBatchImportDate = ingredientBatchImportDate;
		this.ingredientBatchImportNumber = ingredientBatchImportNumber;
		this.ingredientBatchImportPerson = ingredientBatchImportPerson;
		this.ingredientBatchValidatePerson = ingredientBatchValidatePerson;
		this.supplier = supplier;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIngredientBatchName() {
		return ingredientBatchName;
	}

	public void setIngredientBatchName(String ingredientBatchName) {
		this.ingredientBatchName = ingredientBatchName;
	}

	public String getIngredientBatchCode() {
		return ingredientBatchCode;
	}

	public void setIngredientBatchCode(String ingredientBatchCode) {
		this.ingredientBatchCode = ingredientBatchCode;
	}

	public String getIngredientBatchImportDate() {
		return ingredientBatchImportDate;
	}

	public void setIngredientBatchImportDate(String ingredientBatchImportDate) {
		this.ingredientBatchImportDate = ingredientBatchImportDate;
	}

	public String getIngredientBatchImportNumber() {
		return ingredientBatchImportNumber;
	}

	public void setIngredientBatchImportNumber(String ingredientBatchImportNumber) {
		this.ingredientBatchImportNumber = ingredientBatchImportNumber;
	}

	public String getIngredientBatchImportPerson() {
		return ingredientBatchImportPerson;
	}

	public void setIngredientBatchImportPerson(String ingredientBatchImportPerson) {
		this.ingredientBatchImportPerson = ingredientBatchImportPerson;
	}

	public String getIngredientBatchValidatePerson() {
		return ingredientBatchValidatePerson;
	}

	public void setIngredientBatchValidatePerson(String ingredientBatchValidatePerson) {
		this.ingredientBatchValidatePerson = ingredientBatchValidatePerson;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "IngredientBatch [id=" + id + ", ingredientBatchName=" + ingredientBatchName + ", ingredientBatchCode="
				+ ingredientBatchCode + ", ingredientBatchImportDate=" + ingredientBatchImportDate
				+ ", ingredientBatchImportNumber=" + ingredientBatchImportNumber + ", ingredientBatchImportPerson="
				+ ingredientBatchImportPerson + ", ingredientBatchValidatePerson=" + ingredientBatchValidatePerson
				+ "]";
	}

}