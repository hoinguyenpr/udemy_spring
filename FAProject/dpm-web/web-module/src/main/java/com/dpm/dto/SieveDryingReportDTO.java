package com.dpm.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class SieveDryingReportDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private int typeProductId;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fromDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate toDate;

	private int machineId;

	private int ingredientBatchCode;

	private int lotCode;

	private int status;

	public SieveDryingReportDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeProductId() {
		return typeProductId;
	}

	public void setTypeProductId(int typeProductId) {
		this.typeProductId = typeProductId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public int getIngredientBatchCode() {
		return ingredientBatchCode;
	}

	public void setIngredientBatchCode(int ingredientBatchCode) {
		this.ingredientBatchCode = ingredientBatchCode;
	}

	public int getLotCode() {
		return lotCode;
	}

	public void setLotCode(int lotCode) {
		this.lotCode = lotCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SieveDryingReportDTO(int id, int typeProductId, LocalDate fromDate, LocalDate toDate,
			int machineId, int ingredientBatchCode, int lotCode, int status) {
		super();
		this.id = id;
		this.typeProductId = typeProductId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.machineId = machineId;
		this.ingredientBatchCode = ingredientBatchCode;
		this.lotCode = lotCode;
		this.status = status;
	}

	@Override
	public String toString() {
		return "SieveDryingReportDTO [id=" + id + ", typeProductId=" + typeProductId
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", machineId="
				+ machineId + ", ingredientBatchCode=" + ingredientBatchCode
				+ ", lotCode=" + lotCode + ", status=" + status + "]";
	}
	
	
}
