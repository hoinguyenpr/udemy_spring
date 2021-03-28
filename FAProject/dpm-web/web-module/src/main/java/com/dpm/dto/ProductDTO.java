package com.dpm.dto;

import com.dpm.entity.FinishedProductReport;
import com.dpm.entity.Machine;
import com.dpm.entity.TypeProduct;

public class ProductDTO {

	private Integer productId;

	private String productCode;

	private String productName;
	
	private Integer residue;
	
	private Integer coconutMilk;
	
	private Double totalWeightInput;

	private String productEntryDate;

	private FinishedProductReport finishedProductReport;
	
	private TypeProduct typeProduct;
	
	private Machine machine;
	
	


	public ProductDTO(Integer productId, String productCode, String productName,
			Integer residue, Integer coconutMilk, Double totalWeightInput,
			String productEntryDate, FinishedProductReport finishedProductReport,
			TypeProduct typeProduct, Machine machine) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.residue = residue;
		this.coconutMilk = coconutMilk;
		this.totalWeightInput = totalWeightInput;
		this.productEntryDate = productEntryDate;
		this.finishedProductReport = finishedProductReport;
		this.typeProduct = typeProduct;
		this.machine = machine;
	}
	

	public ProductDTO() {
		super();
	}



	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductEntryDate() {
		return productEntryDate;
	}

	public void setProductEntryDate(String productEntryDate) {
		this.productEntryDate = productEntryDate;
	}

	
	public Integer getResidue() {
		return residue;
	}


	public void setResidue(Integer residue) {
		this.residue = residue;
	}


	public Integer getCoconutMilk() {
		return coconutMilk;
	}


	public void setCoconutMilk(Integer coconutMilk) {
		this.coconutMilk = coconutMilk;
	}


	public Double getTotalWeightInput() {
		return totalWeightInput;
	}


	public void setTotalWeightInput(Double totalWeightInput) {
		this.totalWeightInput = totalWeightInput;
	}


	public FinishedProductReport getFinishedProductReport() {
		return finishedProductReport;
	}


	public void setFinishedProductReport(FinishedProductReport finishedProductReport) {
		this.finishedProductReport = finishedProductReport;
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


	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", productCode=" + productCode
				+ ", productName=" + productName + ", residue=" + residue
				+ ", coconutMilk=" + coconutMilk + ", totalWeightInput="
				+ totalWeightInput + ", productEntryDate=" + productEntryDate
				+ ", finishedProductReport=" + finishedProductReport + ", typeProduct="
				+ typeProduct + ", machine=" + machine + "]";
	}

}
