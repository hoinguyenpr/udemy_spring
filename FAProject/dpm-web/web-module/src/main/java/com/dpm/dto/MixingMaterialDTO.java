/**
 * 
 */
package com.dpm.dto;

import javax.validation.constraints.NotNull;

import com.dpm.entity.Additive;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;

/**
 * @author NguyenND6 created 13/1/2021 14:50
 *
 */
public class MixingMaterialDTO  {

	



	/**
	 * 
	 */
	
	private int id;
	@NotNull(message = "Type Produtct is null. Please check again")
	private String typeProduct;
	@NotNull(message = "The date is null. Please check again")
	private String dateMixing;
	@NotNull(message = "Name machine is null. Please check again")
	private String machine;
	@NotNull(message = "Number batch is null. Please check again")
	private String batch;
	@NotNull(message = "Amount Material is null. Please check again")
	private String amountMaterial;
	@NotNull(message = "Name Ingredient is null. Please check again")
	private String ingredientBatch;
	@NotNull(message = "Time mixing is null. Please check again")
	private int mixingTime;
	@NotNull(message = "Employee is null. Please check again")
	private String employee;
	@NotNull(message = "Name Product Lot batch is null. Please check again")
	private String productLot;
	@NotNull(message = "Amount Additive is null. Please check again")
	private String amountAdditive;
	@NotNull(message = "Name Additive is null. Please check again")
	private String additive;
	@NotNull(message = "NO is null. Please check again")
	private String no;
	
	
	private String ingredientBatchCode;
	private String additiveCode;
	private String machineCode;
	private String employeeName;
	private String productLotCode;
	
	public String getIngredientBatchCode() {
		return ingredientBatchCode;
	}
	public void setIngredientBatchCode(String ingredientBatchCode) {
		this.ingredientBatchCode = ingredientBatchCode;
	}
	public String getAdditiveCode() {
		return additiveCode;
	}
	public void setAdditiveCode(String additiveCode) {
		this.additiveCode = additiveCode;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getProductLotCode() {
		return productLotCode;
	}
	public void setProductLotCode(String productLotCode) {
		this.productLotCode = productLotCode;
	}
	private Boolean status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeProduct() {
		return typeProduct;
	}
	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}
	public String getDateMixing() {
		return dateMixing;
	}
	public void setDateMixing(String dateMixing) {
		this.dateMixing = dateMixing;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getAmountMaterial() {
		return amountMaterial;
	}
	public void setAmountMaterial(String amountMaterial) {
		this.amountMaterial = amountMaterial;
	}
	public String getIngredientBatch() {
		return ingredientBatch;
	}
	public void setIngredientBatch(String ingredientBatch) {
		this.ingredientBatch = ingredientBatch;
	}
	public int getMixingTime() {
		return mixingTime;
	}
	public void setMixingTime(int mixingTime) {
		this.mixingTime = mixingTime;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getProductLot() {
		return productLot;
	}
	public void setProductLot(String productLot) {
		this.productLot = productLot;
	}
	public String getAmountAdditive() {
		return amountAdditive;
	}
	public void setAmountAdditive(String amountAdditive) {
		this.amountAdditive = amountAdditive;
	}
	public String getAdditive() {
		return additive;
	}
	public void setAdditive(String additive) {
		this.additive = additive;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MixingMaterialDTO [id=" + id + ", typeProduct=" + typeProduct + ", dateMixing=" + dateMixing
				+ ", machine=" + machine + ", batch=" + batch + ", amountMaterial=" + amountMaterial
				+ ", ingredientBatch=" + ingredientBatch + ", mixingTime=" + mixingTime + ", employee=" + employee
				+ ", productLot=" + productLot + ", amountAdditive=" + amountAdditive + ", additive=" + additive
				+ ", no=" + no + ", status=" + status + "]";
	}
	
	
	
	

}
