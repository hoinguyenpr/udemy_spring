package com.dpm.dto;

import java.io.Serializable;

public class ProductReportDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int typeProductId;

	private int machineId;

	private String fromDate;

	private String toDate;

	public ProductReportDTO() {
		super();
	}

	public int getTypeProductId() {
		return typeProductId;
	}

	public void setTypeProductId(int typeProductId) {
		this.typeProductId = typeProductId;
	}

	public int getMachineId() {

		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public ProductReportDTO(int typeProductId, int machineId, String fromDate,
			String toDate) {
		super();
		this.typeProductId = typeProductId;
		this.machineId = machineId;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

}
